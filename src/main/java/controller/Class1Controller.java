package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.Class1;
import models.Class1Dao;

@WebServlet(urlPatterns = { "/mainLMS/*", "/deptLMS/*", "/classLMS/*" }, initParams = {
		@WebInitParam(name = "view", value = "/views/") })
public class Class1Controller extends MskimRequestMapping {

	private Class1Dao dao = new Class1Dao();

	/*	@RequestMapping("join")
		public String join(HttpServletRequest request, HttpServletResponse response) {
			Class1 cls = new Class1();
			cls.setClass_no(request.getParameter("classNo"));
			cls.setClass_name(request.getParameter("className"));
			cls.setCl
			mem.setId(request.getParameter("id"));
			mem.setPass(request.getParameter("pass"));
			mem.setName(request.getParameter("name"));
			mem.setGender(Integer.parseInt(request.getParameter("gender")));
			mem.setTel(request.getParameter("tel"));
			mem.setEmail(request.getParameter("email"));
			mem.setPicture(request.getParameter("picture"));
			if (dao.insert(mem)) {
				request.setAttribute("msg", mem.getName() + "님 회원 가입 되었습니다.");
				request.setAttribute("url", "loginForm");
			} else {
				request.setAttribute("msg", mem.getName() + "님 회원가입시 오류 발생했습니다.");
				request.setAttribute("url", "joinForm");
			}
			return "alert";
		}*/

	@RequestMapping("signUpClass")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<Class1> list = dao.list();
		request.setAttribute("classesList", list);
		return "mainLMS/signUpClass";
	}
}
