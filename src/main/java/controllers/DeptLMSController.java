package controllers;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;

@WebServlet(urlPatterns = { "/deptLMS/*"}, initParams = {
		@WebInitParam(name = "view", value = "/views/") })
public class DeptLMSController extends MskimRequestMapping {
	
	//오예록
	@RequestMapping("addClass")
	public String addClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 cls = new Class1();
		cls.setClass_name(request.getParameter("className"));
		cls.setClass_no(request.getParameter("classNo"));
		cls.setBan(request.getParameter("classBan"));
		cls.setClass_grade(Integer.parseInt(request.getParameter("classGrade")));
		cls.setCredit(Integer.parseInt(request.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(request.getParameter("maxP")));
		cls.setClassroom(request.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(request.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(request.getParameter("eTime")));
		System.out.println("dfasdfasdf");
		System.out.println(request.getParameter("days"));
	    return "deptLMS/addClass";
	}
//	@RequestMapping("signUpClass")
//	public String list(HttpServletRequest request, HttpServletResponse response) {
//		List<Class1> list = dao.list();
//		request.setAttribute("classesList", list);
//		return "mainLMS/signUpClass";
//	}
}
