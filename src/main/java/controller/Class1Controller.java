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

	@RequestMapping("signUpClass")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<Class1> list = dao.list();
		request.setAttribute("classesList", list);
		return "mainLMS/signUpClass";
	}
}
