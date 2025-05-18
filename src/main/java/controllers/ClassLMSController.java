package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;
import models.classes.Class1Dao;

@WebServlet(urlPatterns = { "/classLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class ClassLMSController extends MskimRequestMapping {

	private Class1Dao dao = new Class1Dao();
	

	/*
	 * @RequestMapping("signUpClass") public String list(HttpServletRequest request,
	 * HttpServletResponse response) { List<Class1> list = dao.list();
	 * request.setAttribute("classesList", list); return "mainLMS/signUpClass"; }
	 */
}
