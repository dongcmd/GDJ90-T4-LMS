package controllers;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.users.User;
import models.users.UserDao;

// 개발자이름
@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class MainLMSController extends MskimRequestMapping{

	// 로그인 아이디 체크 =================================================================
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
}