package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.users.User;
import models.users.UserDao;
/*
	controllers/UserController.java - 김기흔
*/
@WebServlet(urlPatterns = {"/users/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class UserController extends MskimRequestMapping{
	private UserDao dao = new UserDao();
		
	// 로그인 =================================================================
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response) {			
		// 파라미터 조회
		String user_no = request.getParameter("user_no");
		String password = request.getParameter("password");
		User user = dao.selectOne(user_no);
		String msg = null;
		String url = null;
		
		if(user == null){ // 아이디가 없는 경우
			msg = "아이디를 확인하세요";
			url = "loginForm";
		} else if (!password.equals(user.getPassword())) {
			msg = "비밀번호를 확인하세요";
			url = "loginForm";
		} else { 
			request.getSession().setAttribute("login", user);
			msg = user.getUser_name() + "님 반갑습니다.";
			url = "../mainLMS/main";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}		
	// 아이디 찾기=========================================================================
	@RequestMapping("id")
	public String id(HttpServletRequest request, HttpServletResponse response) {
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
			
		String user_no = dao.userSearch(user_name, email);

		  if (user_no == null) {
		        request.setAttribute("msg", "아이디가 없습니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("null");
		        return "alert";
		    } else if ("999".equals(user_no)) {
		        request.setAttribute("msg", "조회할 수 없는 계정입니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("admin");
		        return "alert";
		    } else {
		        request.setAttribute("user_no", user_no);
		        System.out.println("not null");
		        return "users/id";
		    }
	}
	// 비밀번호 초기화 =================================================================
	@RequestMapping("resetpw")
	public String resetpw(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		String email = request.getParameter("email");
		String randpw = null;
			
		//존재하는 계정인지 확인
		String isUser_no = dao.isUser(user_no);	
			
		if(isUser_no != null && isUser_no.equals(user_no)) {
			Random rand = new Random();
			int x = 100000 + rand.nextInt(900000);
			randpw = String.valueOf(x);
			int pass = dao.pwReset(user_no, randpw);
			if(pass == 1) {
				request.setAttribute("msg", "비밀번호:"+ randpw);
				request.setAttribute("close", true);
				return "alert";
			} else {
				request.setAttribute("msg", "오류발생");
				return "users/resetPwForm";
			}
			
		}
		request.setAttribute("msg", "비밀번호를 찾을 수 없습니다.");
		request.setAttribute("url", "resetPwForm");
		return "alert";
	}
	
	// 로그아웃 =================================================================
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		request.setAttribute("msg", "로그아웃되었습니다");
		request.setAttribute("url", "loginForm");
		return "alert";
	}
	
	// 로그인 아이디 체크 =================================================================
	public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		User login = (User)request.getSession().getAttribute("login");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if (!login.getUser_no().equals("999") && !user_no.equals(login.getUser_no())) {
			request.setAttribute("msg", "본인만 조회 가능합니다");
			request.setAttribute("url", "main");
			return "alert";
		}
		return null; // 정상인 경우
	}
	// 로그인 아이디 체크(팝업창) =================================================================
	public String loginIdCheckPopup(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("close", true);
			return "alert";
		}
		return null; // 정상인 경우
	}
	/* 회원정보(DB 에서 가져오기) =================================================================*/
	@RequestMapping("info")
	@MSLogin("loginIdCheckPopup")
	public String info(HttpServletRequest request, HttpServletResponse response) {
		return "users/info";
	}
	// 회원정보 수정 페이지 ====================================================================
		@RequestMapping("updateForm")
		@MSLogin("loginIdCheckPopup") 
		public String updateForm(HttpServletRequest request, HttpServletResponse response) {
			return "users/updateForm";
		}		
	
		/* 회원정보 수정 =================================================================   */
		@RequestMapping("update")
		@MSLogin("loginIdCheckPopup")
		public String update(HttpServletRequest request, HttpServletResponse response) {		
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
			User login = (User)request.getSession().getAttribute("user_no");	
			String msg = "비밀번호가 틀립니다.";
			String url = "users/updateForm";
			// 비밀번호 같을시
			if(user.getPassword().equals(login.getPassword())) {
				if(dao.update(user)) {
					msg = "회원정보 수정완료";
					url = "info";
				} else {
					msg = "회원정보 수정실패";
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "alert";
		}
		
		// 비밀번호 재설정 창 =================================================================
		@RequestMapping("pwForm")
		@MSLogin("loginIdCheckPopup")
		public String pwForm(HttpServletRequest request, HttpServletResponse response) {
			User login = (User) request.getSession().getAttribute("login");
			return "users/pwForm";
		}
	    //비밀번호 재설정 ====================================================================	
		@RequestMapping("updatepw")
		public String updatepw(HttpServletRequest request, HttpServletResponse response) {
			String password = request.getParameter("password");
			String n_pass1 = request.getParameter("new_password1");
			String n_pass2 = request.getParameter("new_password2");
			User login = (User) request.getSession().getAttribute("user_no");		
			
			if(password.equals(login.getPassword())){
		    	 if(!n_pass1.equals("") && !n_pass2.equals("") && n_pass1.equals(n_pass2)) {
		    		if(dao.updatePass(login.getUser_no(), n_pass1)) { // 비밀번호 수정성공
		    			request.setAttribute("msg", "비밀번호 수정성공.");
		    			request.setAttribute("url", "info");
		    			return "alert"; 
		    		} else { // 비밀번호 수정실패
		    			StringBuilder sb = new StringBuilder();
		    			sb.append("alert('비밀번호 수정시 오류가 발생했습니다.');\n");
		    			sb.append("self.close();");
		    			request.setAttribute("script", sb.toString());
		    			return "dummy"; // dummy.jsp 생성
		    		}
		    	} else if (n_pass1.equals("") || n_pass1.equals("")) {
		    		request.setAttribute("msg", "새로운 비밀번호를 입력하세요");
		    		request.setAttribute("url", "pwForm");
		    		return "alert";
		    	}
		    	 else { // 비밀번호 오류
		    		request.setAttribute("msg", "새로운 비밀번호들이 일치하지 않습니다.");
		    		request.setAttribute("url", "pwForm");
		    		return "alert";
		    	} 
		    } else {
				request.setAttribute("msg", "비밀번호가 틀립니다.");
				request.setAttribute("url", "pwForm");
				return "alert";
		    }
		}
	
	// 관리자 탈퇴 방지 =================================================================
	@RequestMapping("deleteForm")
	@MSLogin("loginIdCheck")
	public String deleteForm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if(id.equals("admin")) {
			request.setAttribute("msg", "관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url", "info?id=" + id);
			return "alert";
		}
		return "member/deleteForm";
	}
	
	/* 탈퇴 =================================================================
	1. 2개의 파라미터 정보 저장 (id/pass)
	2. 로그인정보 검증
		- 로그아웃 상태 : 로그인하세요. loginForm.jsp 페이지 이동
		- 본인 탈퇴가능(관리자 제외) : 본인만 탈퇴 가능합니다. main.jsp 페이지 이동
		- 관리자가 탈퇴시 불가 : 관리자는 탈퇴불가합니다.
	3. 비밀번호 검증
		- 로그인 정보로 비밀번호 검증.
		- 비밀번호 불일치 : 비밀번호 오류 메세지 출력. deletForm.jsp로 페이지 이동
	4. db에서 id에 해당하는 회원 정보 삭제
		boolean MemberDao.delete(id)
		탈퇴 성공 
			- 일반사용자 : 로그아웃 실행. 탈퇴메세지 출력, loginForm.jsp로 페이지 이동
			- 관리자    : 탈퇴메세지 출력. list.jsp로 페이지 이동 
		탈퇴 실패 : 
			- 일반사용자 : 탈퇴실패메세지 main.jsp로 페이지 이동 
			- 관리자    : 탈퇴실패메세지 list.jsp로 페이지 이동 
	*/
	@RequestMapping("delete")
	@MSLogin("loginIdCheck")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String login = (String) request.getSession().getAttribute("login");
		
		if(id.equals("admin")) {
			request.setAttribute("msg", "관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url", "info?id=" + id);
			return "alert";
		}
		Member dbMem = dao.selectOne(login);
		String msg = "비밀번호 오류입니다.";
		String url = "deleteForm?id=" + id;
		// 비밀번호가 같을시 
		if(pass.equals(dbMem.getPass())) {
			if(login.equals("admin")) {url = "list";}
			if(dao.delete(id)) {
				msg = id + "회원 탈퇴 성공";
				// 관리자가 아닐경우
				if(!login.equals("admin")) {
					request.getSession().invalidate();
					url = "loginForm";
				}
			} else {
				msg = id + "회원 탈퇴 실패";
				// 관리자가 아닐경우
				if(!login.equals("admin")) {
					url = "main";
				}
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}	
	
}
