package controllers;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;

@WebServlet(urlPatterns = { "/deptLMS/*"}, initParams = {
		@WebInitParam(name = "view", value = "/views/") })
public class DeptLMSController extends MskimRequestMapping {

//	@RequestMapping("signUpClass")
//	public String list(HttpServletRequest request, HttpServletResponse response) {
//		List<Class1> list = dao.list();
//		request.setAttribute("classesList", list);
//		return "mainLMS/signUpClass";
//	}
}
