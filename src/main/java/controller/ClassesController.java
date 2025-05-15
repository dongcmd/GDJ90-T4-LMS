package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes;
import models.classesDao;

@WebServlet(urlPatterns = { "/mainLMS/*", "/deptLMS/*", "/classLMS/*" }, initParams = {
		@WebInitParam(name = "view", value = "/views/") })
public class ClassesController extends MskimRequestMapping {
	private classesDao dao = new classesDao();

	@RequestMapping("signUpClass")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<classes> list = dao.list();
		request.setAttribute("classesList", list);
		return "mainLMS/signUpClass";
	}
}
