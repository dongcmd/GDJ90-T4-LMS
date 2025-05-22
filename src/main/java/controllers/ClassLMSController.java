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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
	   private String putClass1Session(HttpServletRequest request, Class1 class1) {
	      class1 = new Class1(request.getParameter("class_no"),
	            request.getParameter("ban"),
	            Integer.parseInt(request.getParameter("year")),
	            Integer.parseInt(request.getParameter("term")));
	      class1 = class1Dao.selectOne(class1); // class1 객체 db에서 지정
	      if(class1 == null) {
	         request.setAttribute("msg", "class1 세션에 저장 실패");
	         request.setAttribute("url", "mainLMS/main");
	         return "alert";
	      }
	       String prof = class1Dao.selectProf(class1);
	      class1.setProf(prof);
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
	      Class1 class1 = new Class1();
	      String cs = putClass1Session(request, class1); 
	      if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
	      
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
	
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		System.out.println(class1);
		List<Assignment> asList = asDao.selectAsbyClass(class1);
		List<String> r_stuList = asDao.selectReg_Std(class1.getClass_no());
		System.out.println("수강생 이름 :"+r_stuList);
		if(request.getParameter("as_no") != null) {
			int as_no = Integer.parseInt(request.getParameter("as_no"));
			for(String a : r_stuList) { // a : string 형태의 수강생 user_no
			System.out.println(as_no);
			System.out.println(a);
			subAsDao.totStd_list(a, as_no);
			List<Sub_as> subAsList = subAsDao.list(as_no);
			request.setAttribute("selectedAs_no", as_no);
			request.setAttribute("subAsList", subAsList);
			}
		}
		request.setAttribute("asList", asList);

		return "classLMS/manageAs"; // 정상인 경우
	}
	// 과제 추가 접근권한 설정============================================================
	@RequestMapping("addAssignmentForm")
	public String addAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");

		if(login.getRole().equals("1")) {
			request.setAttribute("msg", "접근 권한이 없습니다.");
			request.setAttribute("url","classInfo");
			return "alert";
		}
		Class1Dao C1Dao = new Class1Dao();
		List<Class1> cls = C1Dao.selectByProfessor(login.getUser_no());
		request.setAttribute("cls", cls);
		return null; // 정상인 경우
	}
	
	// 과제 추가============================================================
	@RequestMapping("addAssignment")
	public String addAssignment(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");

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
    
	    as.setClass_no(class1.getClass_no());
	    as.setBan(class1.getBan());
	    as.setYear(class1.getYear());
	    as.setTerm(class1.getTerm());
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
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
	    String password = request.getParameter("password");
	    int as_no = Integer.parseInt(request.getParameter("as_no"));
	    System.out.println(as_no);
	    String msg = "비밀번호가 맞지 않습니다.";
	    String url = "deleteAssignmentForm?as_no=" + as_no;
	    if (password != null && login.getPassword().equals(password)) {
	        List<String> r_stuList = asDao.selectReg_Std(class1.getClass_no());
	        for(String a : r_stuList) {
	           subAsDao.deleteStd_list(a, as_no);
	        }
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
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		
		// 접근권한 넣어야함
		//===========================
		
		List<Assignment> asList = asDao.selectAsbyClass(class1);
		List<Integer> as_noList = asDao.selectAs_no(class1);
		System.out.println(as_noList);
		List<String> filelist = new ArrayList<>();
		for(int as_no : as_noList) {
			String file = asDao.selectFile(login.getUser_no(), as_no);
			filelist.add(file);
		}
//		System.out.println("파일리스트"+filelist);
//		System.out.println("과제리스트"+asList);
//		System.out.println("세션 정보"+class1);

		request.setAttribute("filelist", filelist);
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
	
	@RequestMapping("upload") //commons-fileupload-1.4 jar + commons-io-2.6.jar
	public String upload(HttpServletRequest request , HttpServletResponse response) {
	    User login = (User)request.getSession().getAttribute("login");
	    String path = request.getServletContext().getRealPath("/") + "files/";
	    File uploadDir = new File(path);
	    if (!uploadDir.exists()) uploadDir.mkdirs();
	    int maxSize = 5 * 1024 * 1024; // 5MB
	    String asNo = null;
	    String fileName = null;
	    if (ServletFileUpload.isMultipartContent(request)) {
	        try {
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            factory.setSizeThreshold(1024 * 1024); // 1MB 메모리 임계값
	            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	            ServletFileUpload upload = new ServletFileUpload(factory);
	            upload.setSizeMax(maxSize);

	            List<FileItem> formItems = upload.parseRequest(request);

	            if (formItems != null && formItems.size() > 0) {
	                for (FileItem item : formItems) {
	                    if (item.isFormField()) {
	                        if (item.getFieldName().equals("as_no")) {
	                            asNo = item.getString("utf-8");
	                        }
	                    } else {
	                        fileName = new File(item.getName()).getName();
	                        String filePath = path + fileName;
	                        File storeFile = new File(filePath);
	                        item.write(storeFile);
	                    }
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            request.setAttribute("msg", "파일 업로드 중 오류가 발생했습니다.");
	            request.setAttribute("url", "submitassignment");
	            return "alert";
	        }
	    }
	    Sub_as as = new Sub_as();
	    as.setAs_no(Integer.parseInt(asNo));
	    as.setFile(fileName);
	    as.setUser_no(login.getUser_no());

	    Sub_as existing = asDao.selectSub_as(as.getUser_no(), as.getAs_no());
	    if (existing.getFile() == null) {
	        if (asDao.updateAs(as)) {
	            request.setAttribute("msg", "과제가 제출되었습니다.");
	            request.setAttribute("url", "submitAs");
	        } else {
	            request.setAttribute("msg", "과제제출에 실패하였습니다.");
	            request.setAttribute("url", "submitassignment");
	        }
	    } else {
	        if (asDao.updateAs(as)) {
	            request.setAttribute("msg", "제출된 파일을 수정했습니다.");
	            request.setAttribute("url", "submitAs");
	        } else {
	            request.setAttribute("msg", "수정에 실패하였습니다.");
	            request.setAttribute("url", "submitassignment");
	        }
	    }

	    return "alert";
	}
	
	@RequestMapping("manageScore")
	public String manageScore(HttpServletRequest request , HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		List<Student> studentList = new ArrayList<>(class1.getStudents().values());
		studentList.sort((s1, s2) -> s1.getUser_no().compareTo(s2.getUser_no()));
		// 학번을 오름차순으로 정렬
		request.setAttribute("reg_users", studentList);
		return "classLMS/manageScore";
	}

}