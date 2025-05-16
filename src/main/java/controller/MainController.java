package controller;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;

@WebServlet(urlPatterns = { "/mainLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/view/") })
public class MainController extends MskimRequestMapping {

	// 메인페이지 =================================================================
	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {

		String uri = request.getRequestURI(); // /lms/main/main2
		String context = request.getContextPath(); // /lms
		String path = uri.substring(context.length() + "/mainLMS/".length()); //  main2

		request.setAttribute("currentURI", request.getRequestURI());
		return "mainLMS/" + path;
	}
}