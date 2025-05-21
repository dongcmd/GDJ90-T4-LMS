package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.TreeMap;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.classes.SubAsDao;
import models.classes.Sub_as;
import models.classes.Submitted_Assignments;
import models.users.User;
import models.users.UserDao;

@WebServlet(urlPatterns = { "/classLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class ClassLMSController extends MskimRequestMapping {
	private UserController uc = new UserController();
	private Class1Dao class1Dao = new Class1Dao();
	private AsDao asDao = new AsDao();
	private Reg_classDao rcDao = new Reg_classDao();
	private UserDao userDao = new UserDao();
	private SubAsDao subAsDao = new SubAsDao();

	// 세션에 클래스 넣기
	private String putClass1Session(HttpServletRequest request) {
		Class1 class1 = new Class1(request.getParameter("class_no"),
				request.getParameter("ban"),
				Integer.parseInt(request.getParameter("year")),
				Integer.parseInt(request.getParameter("term")));
		class1 = class1Dao.selectOne(class1); // class1 객체 db에서 지정
		if(class1 == null) {
			request.setAttribute("msg", "class1 세션에 저장 실패");
			request.setAttribute("url", "mainLMS/main");
			return "alert";
		}
		
		class1.setNow_p(rcDao.studentCount(class1)); // 현재원
		class1.setProf(userDao.selectName(class1.getUser_no())); // 교수명
		List<Student> stList = rcDao.studentList(class1);
		Map<String, Student> stMap = new TreeMap<>();
		for(Student st : stList) { stMap.put(st.getUser_no(), st); }
    
		class1.setStudents(stMap); // 클래스에 소속 학생 넣기
		List<Assignment> asList = asDao.list(class1); 
		Map<String, Assignment> asMap = new HashMap<>(); // class1의 과제 목록
		for(Assignment as : asList) { // 과제에 각 제출한 과제 넣기
			Map<String, Sub_as> subAsMap = new HashMap<>();
			for(Student st : stList) { // 제출한 과제 목록에 각 학생 넣기
				subAsMap.put(st.getUser_no(), subAsDao.selectOne(st.getUser_no(), as.getAs_no()));
			}
			as.setSub_as(subAsMap);
			asMap.put(as.getAs_no()+"", as);
		}
		class1.setAssignments(asMap); // 클래스에 각 과제 넣기
		request.getSession().setAttribute("class1", class1); // session 속성으로 class1 지정
		return null;
	}
	
	
	// 세션의 class1 값 체크. 없다면 강의선택페이지로 이동 ===
	public String chkClass1(HttpServletRequest request) {
		if(request.getSession().getAttribute("class1") == null) {
			request.setAttribute("msg", "강의를 선택하세요.");
			request.setAttribute("url", "../deptLMS/classList");
			return "alert"; // 없음
		}
		return null; // 있음.
	}

	@RequestMapping("classInfo")
	public String classInfo(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response);
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		String cs = putClass1Session(request); 
		if(cs != null) { return cs; } // 세션에 클래스 넣기
		String hasClass = chkClass1(request);
		if(hasClass != null) { return hasClass; } // class1 확인
		
		//테스트
		Class1 noClass = (Class1)request.getSession().getAttribute("class1");
		Collection<Student> studentList = noClass.getStudents().values();

		return "classLMS/classInfo";
	}
	
	// 과제관리 접근권한 설정================================================
	@RequestMapping("manageAs")
	public String manageAs(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response);
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		String hasClass = chkClass1(request);
		if(hasClass != null) { return hasClass; } // class1 확인
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
	
		Class1 loginclass = (Class1)request.getSession().getAttribute("class1");
		List<Assignment> asList = asDao.selectAsbyClass(loginclass);
		if(request.getParameter("as_no") != null) {
			int as_no = Integer.parseInt(request.getParameter("as_no"));
			List<Sub_as> subAsList = subAsDao.list(as_no);
			System.out.println(subAsList);
			request.setAttribute("selectedAs_no", as_no);
			request.setAttribute("subAsList", subAsList);
		}
		request.setAttribute("asList", asList);

		return "classLMS/manageAs"; // 정상인 경우
	}
	// 과제 추가 접근권한 설정============================================================
	@RequestMapping("addAssignmentForm")
	public String addAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		Class1 loginclass = (Class1)request.getSession().getAttribute("class1");

		if(login.getRole().equals("1")) {
			request.setAttribute("msg", "접근 권한이 없습니다.");
			request.setAttribute("url","classInfo");
			return "alert";
		}
		// 여기 부분은 추후에 수정해야할듯!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Class1Dao C1Dao = new Class1Dao();
		List<Class1> cls = C1Dao.selectByProfessor(login.getUser_no());
		request.setAttribute("cls", cls);
		return null; // 정상인 경우
	}
	
	// 과제 추가============================================================
	@RequestMapping("addAssignment")
	public String addAssignment(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		Class1 loginclass = (Class1)request.getSession().getAttribute("class1");

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
    
	    as.setClass_no(loginclass.getClass_no());
	    as.setBan(loginclass.getBan());
	    as.setYear(loginclass.getYear());
	    as.setTerm(loginclass.getTerm());
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
	public String udpateAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		if(login.getRole().equals("1")) {
			request.setAttribute("msg", "접근 권한이 없습니다.");
			request.setAttribute("url","classInfo");
			return "alert";
		}
		int as_no = Integer.parseInt(request.getParameter("as_no"));
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
	
	//과제제출(학생) 메인폼 =============================================
	@RequestMapping("submitAs")
	public String submitAs(HttpServletRequest request , HttpServletResponse response) {				
		User login = (User)request.getSession().getAttribute("login");
		Class1 loginclass = (Class1)request.getSession().getAttribute("class1");
    
		System.out.println(loginclass);
		// 접근권한 넣어야함
		//===========================
		
		List<Assignment> asList = asDao.selectAsbyClass(loginclass);
//		if(request.getParameter("as_no") != null) {
//			//as_no = Integer.parseInt(request.getParameter("as_no"));
//			List<Sub_as> subAsList = subAsDao.list(as_no);
//			System.out.println(subAsList);
//			request.setAttribute("selectedAs_no", as_no);
//			request.setAttribute("subAsList", subAsList);
//		}
		System.out.println("과제리스트"+asList);
		System.out.println("세션 정보"+loginclass);

		request.setAttribute("asList", asList);
		return "classLMS/submitAs";
	}
	
	//과제제출(학생) 제출폼 =============================================
	@RequestMapping("submitassignment")
	public String submitassignment(HttpServletRequest request , HttpServletResponse response) {				
		User login = (User)request.getSession().getAttribute("login");
		Class1 loginclass = (Class1)request.getSession().getAttribute("class1");
		// 접근권한 넣어야함 ==========================
		int as_no = Integer.parseInt(request.getParameter("as_no"));
		Assignment as1 = asDao.selectOne(as_no);
		request.setAttribute("as", as1);
		return "classLMS/submitassignment";
	}
	
	@RequestMapping("upload")
	public String upload(HttpServletRequest request , HttpServletResponse response) {	
		User login = (User)request.getSession().getAttribute("login");
		String path = request.getServletContext().getRealPath("/") +"files/";
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		int size = 5*1024*1024;
		MultipartRequest multi = null;
		try { multi = new MultipartRequest(request, path, size, "utf-8");
		} catch(IOException e) { e.printStackTrace(); }
		String asNo = multi.getParameter("as_no");
		String fileName = multi.getOriginalFileName("file");
		Sub_as as = new Sub_as();
		as.setAs_no(Integer.parseInt(asNo));
		as.setFile(fileName);
		as.setUser_no(login.getUser_no());
		Sub_as existing = asDao.selectSub_as(as.getUser_no(), as.getAs_no());
		if (existing == null) {
			if(asDao.insertAs(as)) {
				request.setAttribute("msg", "과제가 제출되었습니다.");
				request.setAttribute("url", "submitAs");
			} else {
				request.setAttribute("msg", "과제제출에 실패하였습니다.");
				request.setAttribute("url", "submitassignment");
			}
		} else {
			if(asDao.updateAs(as)) {
				request.setAttribute("msg", "제출된 파일을 수정했습니다.");
				request.setAttribute("url", "submitAs");
			} else {
				request.setAttribute("msg", "수정에 실패하였습니다.");
				request.setAttribute("url", "submitassignment");
			}
		}
		return "alert";
	}
	
	@RequestMapping("manage")
	public String manage(HttpServletRequest request , HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		List<Student> studentList = new ArrayList<>(class1.getStudents().values());
		studentList.sort((s1, s2) -> s1.getUser_no().compareTo(s2.getUser_no()));
		// 학번을 오름차순으로 정렬
		request.setAttribute("reg_users", studentList);
		return "classLMS/manage";
	}

}