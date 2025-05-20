package controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.users.User;
import models.users.UserDao;

@WebServlet(urlPatterns = { "/classLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class ClassLMSController extends MskimRequestMapping {
	private UserController uc = new UserController();
	private Class1Dao class1Dao = new Class1Dao();
	private AsDao asDao = new AsDao();
	
	// 세션의 class1 값 체크. 없다면 강의선택페이지로 이동 ===
	public String chkClass1(HttpServletRequest request) {
		if(request.getSession().getAttribute("class1") == null) {
			request.setAttribute("msg", "강의를 선택하세요.");
			request.setAttribute("url", "../deptLMS/classList");
			return "alert"; // 없음
		}
		return null; // 있음.
	}
	
// login 체크(지울 예정) 수정필요 =================================================================
public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
	User login = (User)request.getSession().getAttribute("login");
if(login == null) {
	request.setAttribute("msg", "로그인 하세요");
request.setAttribute("url", "../users/loginForm");
return "alert";
}
return null; // 정상인 경우
}
	@RequestMapping("classInfo")
	public String classInfo(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response);
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		
		Class1 class1 = new Class1(request.getParameter("class_no"),
				request.getParameter("ban"),
				Integer.parseInt(request.getParameter("year")),
				Integer.parseInt(request.getParameter("term")));
		class1 = class1Dao.selectOne(class1); // class1 객체 db에서 지정
		
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		request.getSession().setAttribute("class1", class1); // session 속성으로 class1 지정
		return "classLMS/classInfo";
	}
			
			// 과제관리 접근권한 설정================================================
			@RequestMapping("manageAs")
			public String manageAs(HttpServletRequest request, HttpServletResponse response) {
				String loginCheck = uc.loginIdCheck(request, response);
				if(loginCheck != null) { return loginCheck; } // 로그인 확인
				String profCheck = uc.profCheck(request, response);
				if(profCheck != null) { return profCheck; } // 교수 확인
				chkClass1(request); // class1 확인
				String classCheck = uc.classCheck(request, response);
				if(classCheck != null) { return classCheck; } // 강의 확인
			
				Class1 class1 = new Class1();
				class1.setClass_no("1001");
				class1.setBan("A");
				class1.setYear(2025);
				class1.setTerm(1);
				
				List<Assignment> asList = asDao.list(class1);
				request.setAttribute("asList", asList);
				return "classLMS/manageAS"; // 정상인 경우
			}
			// 과제 추가 접근권한 설정============================================================
			@RequestMapping("addAssignmentForm")
			@MSLogin("loginIdCheck")
			public String addAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
				User login = (User)request.getSession().getAttribute("login");
				if(login.getRole().equals("1")) {
					request.setAttribute("msg", "접근 권한이 없습니다.");
					request.setAttribute("url","classInfo");
					return "alert";
				}
				// 여기 부분은 추후에 수정해야할듯!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				Class1Dao C1Dao = new Class1Dao();
				List<Class1> cls = C1Dao.selectByProfessor(login.getUser_no());
				System.out.println(cls);
				request.setAttribute("cls", cls);
				return null; // 정상인 경우
			}
			
			// 과제 추가============================================================
			@RequestMapping("addAssignment")
			@MSLogin("loginIdCheck")
			public String addAssignment(HttpServletRequest request, HttpServletResponse response) {
				User login = (User)request.getSession().getAttribute("login");
				AsDao dao = new AsDao();	    
				Assignment as = new Assignment();
			    as.setAs_name(request.getParameter("as_name"));
			    as.setAs_content(request.getParameter("as_content"));
			    
			    String as_s_date_str = request.getParameter("as_s_date");
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			    LocalDateTime localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
			    ZoneId zoneId = ZoneId.systemDefault(); // 서버 시스템 타임존
			    Date as_s_date = Date.from(localDateTime.atZone(zoneId).toInstant());
			    as.setAs_s_date(as_s_date);
			    
			    String as_e_date_str = request.getParameter("as_e_date");
			    localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
			    Date as_e_date = Date.from(localDateTime.atZone(zoneId).toInstant());
			    as.setAs_e_date(as_e_date);
			    
			    as.setAs_point(Integer.parseInt(request.getParameter("as_point")));
			    as.setClass_no(request.getParameter("class_no"));
			    as.setBan(request.getParameter("ban"));
			    as.setYear(Integer.parseInt(request.getParameter("year")));
			    as.setTerm(Integer.parseInt(request.getParameter("term")));
				if(dao.insert(as)) {
					request.setAttribute("msg","과제를 추가하였습니다.");
					request.setAttribute("url", "manageAs");
				} else {
					request.setAttribute("msg", "오류 발생");
					request.setAttribute("url", "addAssignmentForm");
				}
				return "alert";				
			}
			
			// 과제 수정 권한 업데이트 및 요청 객체 전달============================================================
			@RequestMapping("updateAssignmentForm")
			@MSLogin("loginIdCheck")
			public String udpateAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
				User login = (User)request.getSession().getAttribute("login");
				if(login.getRole().equals("1")) {
					request.setAttribute("msg", "접근 권한이 없습니다.");
					request.setAttribute("url","classInfo");
					return "alert";
				}
				int as_no = Integer.parseInt(request.getParameter("as_no"));
				System.out.println(as_no);
				Assignment as1 = asDao.selectOne(as_no);
				//시작날짜, 끝나는 날짜는 따로 저장
				LocalDateTime as_s_date = as1.getAs_s_date().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
				LocalDateTime as_e_date = as1.getAs_e_date().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
				request.setAttribute("as_s_date", as_s_date);
				request.setAttribute("as_e_date", as_e_date);
				request.setAttribute("as1", as1);
				return null; // 정상인 경우
			}
			
			//과제 수정 =========================================================
			@RequestMapping("updateAssignment")
			public String updateuser(HttpServletRequest request, HttpServletResponse response) {
				AsDao dao = new AsDao();	   
				Assignment as = new Assignment();
				as.setAs_no(Integer.parseInt(request.getParameter("as_no")));
			    as.setAs_name(request.getParameter("as_name"));
			    as.setAs_content(request.getParameter("as_content"));
			    
			    String as_s_date_str = request.getParameter("as_s_date");
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			    LocalDateTime localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
			    ZoneId zoneId = ZoneId.systemDefault(); // 서버 시스템 타임존
			    Date as_s_date = Date.from(localDateTime.atZone(zoneId).toInstant());
			    as.setAs_s_date(as_s_date);
			    
			    String as_e_date_str = request.getParameter("as_e_date");
			    localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
			    Date as_e_date = Date.from(localDateTime.atZone(zoneId).toInstant());
			    as.setAs_e_date(as_e_date);
			    
			    as.setAs_point(Integer.parseInt(request.getParameter("as_point")));
			    as.setClass_no(request.getParameter("class_no"));
			    as.setBan(request.getParameter("ban"));
			    as.setYear(Integer.parseInt(request.getParameter("year")));
			    as.setTerm(Integer.parseInt(request.getParameter("term")));
				if(dao.update(as)) {
					request.setAttribute("msg","과제를 수정하였습니다.");
					request.setAttribute("url", "manageAs");
				} else {
					request.setAttribute("msg", "오류 발생");
					request.setAttribute("url", "updateAssignmentForm");
				}
				return "alert";		
			}
			// 과제 삭제폼 =====================================================
			@RequestMapping("deleteAssignmentForm")
			@MSLogin("loginIdCheck")
			public String deleteAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
				User login = (User)request.getSession().getAttribute("login");
				if(login.getRole().equals("1")) {
					request.setAttribute("msg", "접근 권한이 없습니다.");
					request.setAttribute("url","classInfo");
					return "alert";
				}
				int as_no = Integer.parseInt(request.getParameter("as_no"));
				Assignment as = asDao.selectOne(as_no);
				request.setAttribute("as", as);
				return null; // 정상인 경우
			}
			
			// 과제 삭제===========================================================
			@RequestMapping("deleteAssignment")
			public String deleteAssignment(HttpServletRequest request , HttpServletResponse response) {
				User login = (User)request.getSession().getAttribute("login");
			    String password = request.getParameter("password");
			    int as_no = Integer.parseInt(request.getParameter("as_no"));
			    String msg = "비밀번호가 맞지 않습니다.";
			    String url = "deleteAssignmentForm";
			    if (password != null && login.getPassword().equals(password)) {
			        boolean result = asDao.deleteAssignment(as_no);
			        if (result) {
			            msg = "사용자 삭제 완료";
			            url = "manageAs";
			        } else {
			            msg = "사용자 삭제 실패";
			        }
			    }
			    request.setAttribute("msg", msg);
			    request.setAttribute("url", url);
			    return "alert";
			}
	
	/*
	 * @RequestMapping("signUpClass") public String list(HttpServletRequest request,
	 * HttpServletResponse response) { List<Class1> list = dao.list();
	 * request.setAttribute("classesList", list); return "mainLMS/signUpClass"; }
	 */
}
