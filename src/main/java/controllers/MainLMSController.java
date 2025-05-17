package controllers;


import java.util.List;
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


@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class MainLMSController extends MskimRequestMapping{

	private UserDao dao = new UserDao();
	//mainLayout 로그인 정보 전달============================
	@RequestMapping("MainLayout")
	public String MainLayout(HttpServletRequest request, HttpServletResponse response) {
			String login = (String) request.getSession().getAttribute("login");
			User user = dao.selectOne(login);
			request.setAttribute("user", user);  
			return "main";		
	}
	
	// 로그인 아이디 체크 =================================================================
		public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
			String user_no = request.getParameter("user_no");
			String login = (String)request.getSession().getAttribute("login");
			if(login == null) {
				request.setAttribute("msg", "로그인 하세요");
				request.setAttribute("url", "loginForm");
				return "alert";
			} else if (!login.equals("999") && !user_no.equals(login)) {
				request.setAttribute("msg", "본인만 조회 가능합니다");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null; // 정상인 경우
		}
	//관리자 검증
		public String loginAdminCheck(HttpServletRequest request, HttpServletResponse response) {
			String login = (String) request.getSession().getAttribute("login");
			if(login == null) {
				request.setAttribute("msg", "로그인 하세요");
				request.setAttribute("url", "../users/loginForm");
				return "alert";
			} else if (!login.equals("999")) {
				request.setAttribute("msg", "관리자만 가능한 업무입니다.");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null;
		}

	// 메인페이지 로그인검증 =================================================================
	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		String login = (String)request.getSession().getAttribute("login");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url","../users/loginForm");
			return "alert";
		}
		return null; // 정상인 경우
	}

	// 관리자가 아니면 접근 불가 기능=================================================
	@RequestMapping("adminForm")
	public String adminForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("login");
		if(login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "openeralert";
		} else if(login != "999") {
			request.setAttribute("msg", "관리자만 접근 가능합니다");
			request.setAttribute("url", "main");			
		}
		List<User> users = dao.selectAll();
		request.setAttribute("users", users);
		System.out.println(users);
		return null;
	}
	//사용자 추가 ====================================================================
	@RequestMapping("addUser")
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
	//사용자 삭제 ========================================================
	@RequestMapping("deleteuser")
	public String deleteUser(HttpServletRequest request) {
	    String user_no = request.getParameter("user_no");
	    String password = request.getParameter("password");
	    String login = (String) request.getSession().getAttribute("login");
	    User admin = dao.selectOne("login");
	    
	    String msg = "비밀번호가 맞지 않습니다.";
	    String url = "adminForm";

	    if (password != null && admin.getPassword().equals(password)) {
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
	private EventDao dao_e = new EventDao();
	@RequestMapping("event")
	public String event(HttpServletRequest request, HttpServletResponse response) {
		Event event = new Event();
		event.setEvent_name(request.getParameter("event_name"));
		event.setEven_s_date(LocalDateTime.parse(request.getParameter("even_s_date")));
		event.setEven_e_date(LocalDateTime.parse(request.getParameter("even_e_date")));
		return null; 
	}
}