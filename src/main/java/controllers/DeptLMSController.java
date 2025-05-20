package controllers;

import java.util.Arrays;
import java.util.Calendar;
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
	private Class1Dao class1Dao = new Class1Dao();
	private UserController uc = new UserController();

	@RequestMapping("addClass1")
	public String addClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 cls = new Class1();
		User login = (User) request.getSession().getAttribute("login");

		cls.setMajor_no(login.getMajor_no());
		cls.setUser_no(login.getUser_no());

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;
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

		String[] dayArr = request.getParameterValues("days");
		if (dayArr != null) {
			List<Integer> days = Arrays.stream(dayArr).map(Integer::valueOf).collect(Collectors.toList());
			cls.setDays(days);
		}

		// 3) DAO 호출 및 결과 처리
		if (class1Dao.insertClass(cls)) {
			request.setAttribute("msg", "강의가 정상적으로 추가되었습니다.");
			request.setAttribute("url", "myClass");
		} else {
			request.setAttribute("msg", "강의 추가 중 오류가 발생했습니다.");
			request.setAttribute("url", "addClass");
		}
		return "alert";
	}

	@RequestMapping("myClass")
	public String myClasses(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		String userNo = login.getUser_no();
		List<Class1> classesList = class1Dao.selectByProfessor(userNo);

		request.setAttribute("classesList", classesList);
		return "deptLMS/myClass";
	}

	@RequestMapping("updateClass")
	public String showUpdateForm(HttpServletRequest request, HttpServletResponse response) {
		Class1 key = new Class1();
		key.setClass_no(request.getParameter("no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));

		Class1 cls = class1Dao.selectOne(key);
		request.setAttribute("cls", cls);
		return "deptLMS/updateClass";
	}

	@RequestMapping("updateClass1")
	public String updateClass1(HttpServletRequest request, HttpServletResponse response) {
		Class1 cls = new Class1();
		cls.setClass_name(request.getParameter("className"));
		cls.setClass_no(request.getParameter("classNo"));
		cls.setBan(request.getParameter("classBan"));
		cls.setYear(Integer.parseInt(request.getParameter("year")));
		cls.setTerm(Integer.parseInt(request.getParameter("term")));
		cls.setCredit(Integer.parseInt(request.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(request.getParameter("maxP")));
		cls.setClassroom(request.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(request.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(request.getParameter("eTime")));
		cls.setC_plan(request.getParameter("courseSyllabus"));
		cls.setFile(request.getParameter("file1"));

		String[] daysArr = request.getParameterValues("days");
		if (daysArr != null) {
			List<Integer> days = Arrays.stream(daysArr).map(Integer::valueOf).collect(Collectors.toList());
			cls.setDays(days);
		}

		if (class1Dao.update(cls)) {
			request.setAttribute("msg", "강의가 정상적으로 수정 성공하였습니다..");
			request.setAttribute("url", "myClass");
		} else {
			request.setAttribute("msg", "강의를 수정 실패하였습니다.");
			request.setAttribute("url", "updateClass");
		}
		return "alert";
	}

	// 강의 삭제 - 교수
	@RequestMapping("deleteClass")
	public String deleteClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 key = new Class1();
		key.setClass_no(request.getParameter("no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));

		if (class1Dao.delete(key)) {
			request.setAttribute("msg", "강의가 정상적으로 삭제되었습니다.");
		} else {
			request.setAttribute("msg", "강의 삭제에 실패했습니다.");
		}
		request.setAttribute("url", "myClass");
		return "alert";
	}

	// 수강 목록 - 학생
	@RequestMapping("classList")
	public String classList(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;

		Class1 cls = new Class1();
		cls.setUser_no(login.getUser_no());
		cls.setYear(year);
		cls.setTerm(term);

		List<Class1> classesList = class1Dao.nowClassList(cls);
		request.setAttribute("classesList", classesList);
		return "deptLMS/classList";
	}

	// 수강 신청 취소
	@RequestMapping("dropClass")
	public String dropClass(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		Class1 key = new Class1();
		key.setUser_no(login.getUser_no());
		key.setClass_no(request.getParameter("no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));
		if (class1Dao.cancelClass(key)) {
			request.setAttribute("msg", "강의가 정상적으로 취소되었습니다.");
		} else {
			request.setAttribute("msg", "수강 신청 취소를 실패했습니다.");
		}
		request.setAttribute("url", "classList");
		return "alert";
	}
}
