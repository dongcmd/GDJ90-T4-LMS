package controllers;

import java.time.LocalDateTime;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.others.Event;
import models.others.EventDao;


@WebServlet(urlPatterns = {"/mainLMS/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class MainLMSController extends MskimRequestMapping{
	
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