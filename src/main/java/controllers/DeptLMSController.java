package controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.users.User;

@WebServlet(urlPatterns = { "/deptLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class DeptLMSController extends MskimRequestMapping {
	private Class1Dao class1Dao = new Class1Dao();
	private UserController uc = new UserController();
	
	@RequestMapping("addClass")
	public String addClass(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		User login = (User) request.getSession().getAttribute("login");
		if(profCheck != null) { return profCheck; }
		return "deptLMS/addClass";
	}
	// 강의 추가 기능 - 교수
	@RequestMapping("addClass1")
	public String addClass1(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		Class1 cls = new Class1();
		User login = (User) request.getSession().getAttribute("login");
		String path = request.getServletContext().getRealPath("/") + "/files/";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		cls.setMajor_no(login.getMajor_no());
		cls.setUser_no(login.getUser_no());

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;
		cls.setYear(year);
		cls.setTerm(term);

		cls.setClass_name(multi.getParameter("className"));
		cls.setClass_no(multi.getParameter("classNo"));
		cls.setBan(multi.getParameter("classBan"));
		cls.setClass_grade(Integer.parseInt(multi.getParameter("classGrade")));
		cls.setCredit(Integer.parseInt(multi.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(multi.getParameter("maxP")));
		cls.setClassroom(multi.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(multi.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(multi.getParameter("eTime")));
		cls.setC_plan(multi.getParameter("courseSyllabus"));
		cls.setFile(multi.getFilesystemName("file1"));
		cls.setS_date(Date.valueOf(multi.getParameter("sDate")));
		cls.setE_date(Date.valueOf(multi.getParameter("eDate")));
		String[] dayArr = multi.getParameterValues("days");
		if (dayArr != null) {
			List<Integer> days = Arrays.stream(dayArr).map(Integer::valueOf).collect(Collectors.toList());
			cls.setDays(days);
		}

		if (class1Dao.insertClass(cls)) {
			request.setAttribute("msg", "강의가 정상적으로 추가되었습니다.");
			request.setAttribute("url", "myClass");
		} else {
			request.setAttribute("msg", "강의 추가 중 오류가 발생했습니다.");
			request.setAttribute("url", "addClass");
		}
		return "alert";
	}
	
	// 자신의 강의 목록 - 교수
	@RequestMapping("myClass")
	public String myClasses(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		User login = (User) request.getSession().getAttribute("login");
		String userNo = login.getUser_no();
		List<Class1> classesList = class1Dao.selectByProfessor(userNo);

		request.setAttribute("classesList", classesList);
		return "deptLMS/myClass";
	}
	
	// 강의 수정 - 교수
	@RequestMapping("updateClass")
	public String showUpdateForm(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		Class1 key = new Class1();
		key.setClass_no(request.getParameter("no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));

		Class1 cls = class1Dao.selectOne(key);
		request.setAttribute("cls", cls);
		return "deptLMS/updateClass";
	}
	
	// 강의 수정 기능 - 교수
	@RequestMapping("updateClass1")
	public String updateClass1(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		String path = request.getServletContext().getRealPath("/") + "/files/";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Class1 cls = new Class1();
		cls.setClass_name(multi.getParameter("className"));
		cls.setClass_no(multi.getParameter("classNo"));
		cls.setBan(multi.getParameter("classBan"));
		cls.setYear(Integer.parseInt(multi.getParameter("year")));
		cls.setTerm(Integer.parseInt(multi.getParameter("term")));
		cls.setCredit(Integer.parseInt(multi.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(multi.getParameter("maxP")));
		cls.setClassroom(multi.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(multi.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(multi.getParameter("eTime")));
		cls.setC_plan(multi.getParameter("courseSyllabus"));
		cls.setFile(multi.getFilesystemName("file1"));
		System.out.println("업로드된 파일명: " + multi.getFilesystemName("file1"));
		System.out.println("실제 저장 경로: " + path);
		cls.setS_date(Date.valueOf(multi.getParameter("sDate")));
		cls.setE_date(Date.valueOf(multi.getParameter("eDate")));

		String[] daysArr = multi.getParameterValues("days");
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
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
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
