package controllers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import models.users.User;

@WebServlet(urlPatterns = { "/deptLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class DeptLMSController extends MskimRequestMapping {
	private Class1Dao dao = new Class1Dao();

	@RequestMapping("addClass1")
	public String addClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 cls = new Class1();
		
		User login = (User) request.getSession().getAttribute("login");
		cls.setMajor_no(login.getMajor_no());
	    cls.setUser_no (login.getUser_no());
		
	    Calendar cal = Calendar.getInstance();
	    int year  = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int term  = (month >= 3 && month <= 8) ? 1 : 2;
	    cls.setYear(year);
	    cls.setTerm(term);
	    
		cls.setClass_name(request.getParameter("className"));
		cls.setClass_no(request.getParameter("classNo"));
		cls.setBan(request.getParameter("classBan"));
		cls.setClass_grade(Integer.parseInt(request.getParameter("classGrade")));
		cls.setCredit(Integer.parseInt(request.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(request.getParameter("maxP")));
		cls.setClassroom(request.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(request.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(request.getParameter("eTime")));
		cls.setC_plan(request.getParameter("courseSyllabus"));
		cls.setFile(request.getParameter("file1"));

		// 2) 체크된 요일(0=월…4=금) → List<Integer>로 변환
		String[] dayArr = request.getParameterValues("days");
		if (dayArr != null) {
			List<Integer> days = Arrays.stream(dayArr).map(Integer::valueOf).collect(Collectors.toList());
			cls.setDays(days);
		}

		// 3) DAO 호출 및 결과 처리
		if (dao.insert(cls)) {
			request.setAttribute("msg", "강의가 정상적으로 추가되었습니다.");
			request.setAttribute("url", "addClass");
		} else {
			request.setAttribute("msg", "강의 추가 중 오류가 발생했습니다.");
			request.setAttribute("url", "addClass");
		}
		return "alert";
	}
}
