package controllers;


import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;

import models.users.User;
import models.users.UserDao;

import models.others.Event;
import models.others.EventDao;
import models.others.Notification;


@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class MainLMSController extends MskimRequestMapping{

	private UserDao dao = new UserDao();
	
	// 로그인 아이디 체크 =================================================================
		public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
			String user_no = request.getParameter("user_no");
			User login = (User)request.getSession().getAttribute("login");
			if(login == null) {
				request.setAttribute("msg", "로그인 하세요");
				request.setAttribute("url", "../users/loginForm");
				return "alert";
			} else if (!login.getUser_no().equals("999") && !user_no.equals(login.getUser_no())) {
				request.setAttribute("msg", "본인만 조회 가능합니다");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null; // 정상인 경우
		}
	//관리자 검증
		public String loginAdminCheck(HttpServletRequest request, HttpServletResponse response) {
			User login = (User) request.getSession().getAttribute("login");
			if(login == null) {
				request.setAttribute("msg", "로그인 하세요");
				request.setAttribute("url", "../users/loginForm");
				return "alert";
			} else if (!login.getUser_no().equals("999")) {
				request.setAttribute("msg", "관리자만 가능한 업무입니다.");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null;
		}

	// 메인페이지 로그인검증 =================================================================
	@RequestMapping("main")
	@MSLogin("loginIdCheck")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		User login = (User)request.getSession().getAttribute("login");
		return null;
	}

	// 사용자 관리 폼(관리자 외 접근권한 없음)=================================================
	@RequestMapping("adminForm")
	@MSLogin("loginAdminCheck")
	public String adminForm(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		List<User> users = dao.selectAll();
		request.setAttribute("users", users);
		System.out.println(users);
		return null;
	}

	// 사용자 추가 폼 =================================================================
	@RequestMapping("addUserForm")
	@MSLogin("loginAdminCheck")
	public String addUserForm(HttpServletRequest request, HttpServletResponse response) {;
		return "mainLMS/addUserForm";
	}	
	//사용자 추가 ====================================================================
	@RequestMapping("adduser")
	@MSLogin("loginAdminCheck")
	public String addUser(HttpServletRequest request, HttpServletResponse response) {
	    User user = new User();
	    user.setUser_no(request.getParameter("user_no"));
	    user.setUser_name(request.getParameter("user_name"));
	    user.setPassword(request.getParameter("password"));
	    user.setRole(request.getParameter("role"));
	    user.setGender(Integer.parseInt(request.getParameter("gender")));
	    user.setMajor_no(request.getParameter("major_no"));
	    String grade = request.getParameter("grade");
	    user.setGrade(grade != null && !grade.isEmpty() ? Integer.parseInt(grade) : 0);
	    user.setEmail(request.getParameter("email"));
	    user.setTel(request.getParameter("tel"));
		
		if(dao.insert(user)) {
			request.setAttribute("msg", user.getUser_name()+"님을 사용자로 추가하였습니다.");
			request.setAttribute("url", "adminForm");
		} else {
			request.setAttribute("msg", "오류 발생");
			request.setAttribute("url", "addUserForm");
		}
		return "alert";
	}
	// 사용자 수정 폼 =====================================================
	@RequestMapping("updateUserForm")
	@MSLogin("loginAdminCheck")
	public String updateUserForm(HttpServletRequest request, HttpServletResponse response) {;
	String user_no = request.getParameter("user_no");
	User user = dao.selectOne(user_no);
	request.setAttribute("user", user);
	return "mainLMS/updateUserForm";
	}
	// 사용자 정보 수정(관리자권한)============================================
	@RequestMapping("updateuser")
	@MSLogin("loginAdminCheck")
	public String updateuser(HttpServletRequest request, HttpServletResponse response) {
	User user = new User();
	user.setUser_no(request.getParameter("user_no"));
	user.setUser_name(request.getParameter("user_name"));
	user.setGender(Integer.parseInt(request.getParameter("gender")));
	user.setTel(request.getParameter("tel"));
	user.setEmail(request.getParameter("email"));
	user.setGrade(Integer.parseInt(request.getParameter("grade")));
	user.setMajor_no(request.getParameter("major_no"));
	user.setPassword(request.getParameter("password"));
	// 비밀번호를 위한 db의 데이터 조회. : login 정보로 조회하기
	User login = (User)request.getSession().getAttribute("login");	
	String msg = "비밀번호가 틀립니다.";
	String url = "users/updateForm";
	// 비밀번호 같을시
	if(user.getPassword().equals(login.getPassword())) {
		if(dao.update(user)) {
			msg = "회원정보 수정완료";
			url = "adminForm";
		} else {
			msg = "회원정보 수정실패";
		}
	}
	request.setAttribute("msg", msg);
	request.setAttribute("url", url);
	return "alert";
	}		
	//사용자 삭제(관리자 권한) ========================================================
	@RequestMapping("deleteuser")
	@MSLogin("loginAdminCheck")
	public String deleteUser(HttpServletRequest request) {
	    String user_no = request.getParameter("user_no");
	    String password = request.getParameter("password");
	    User login = (User) request.getSession().getAttribute("login");
	    String msg = "비밀번호가 맞지 않습니다.";
	    String url = "adminForm";
	    if (password != null && login.getPassword().equals(password)) {
	        boolean result = dao.delete(user_no);
	        if (result) {
	            msg = "사용자 삭제 완료";
	        } else {
	            msg = "사용자 삭제 실패";
	        }
	    }
	    request.setAttribute("msg", msg);
	    request.setAttribute("url", url);
	    return "alert";
	}
	// 원동인
	private EventDao eventDao = new EventDao();
	private Notification NotifiDao = new Notification();
	
	@RequestMapping("event")
	@MSLogin("loginAdminCheck")
	public String event(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
        // 날짜 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Event event = new Event();
        event.setEvent_name(request.getParameter("event_name"));
        event.setEven_s_date(formatter.parse(request.getParameter("event_s_date") + " 00:00:00"));
        event.setEven_e_date(formatter.parse(request.getParameter("event_e_date") + " 23:59:59"));

        // DAO 호출 등 처리
        if(eventDao.insert(event)) {
        	request.setAttribute("msg", event.getEvent_name()+"님을 사용자로 추가하였습니다.");
			request.setAttribute("url", "main");
        } else {
        	request.setAttribute("msg", "오류 발생");
			request.setAttribute("url", "event");
		}
        return "alert"; 
	}
}