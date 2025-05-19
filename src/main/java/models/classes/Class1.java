package models.classes;

import java.util.List;
import java.util.Set;

//오예록
public class Class1 {
	private String class_no; // 강의코드
	private String ban; // 반
	private int year; // 연도
	private int term; // 학기
	private String major_no; // 학과코드
	private String user_no; // 교수번호
	private String class_name; // 강의명
	private int class_grade; // 학년
	private int credit; // 이수학점
	private String classroom; // 강의실
	private int s_time; // 시작교시
	private int e_time; // 종료교시
	private int now_p; // 현재인원
	private int max_p; // 정원
	private String c_plan; // 강의계획
	private String file; // 첨부파일
	private String prof; // 교수명
	private List<Integer> days;
	private Set<Student> students; // 소속 학생들-이동원

	public String getClass_no() {
		return class_no;
	}

	public void setClass_no(String class_no) {
		this.class_no = class_no;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public String getMajor_no() {
		return major_no;
	}

	public void setMajor_no(String major_no) {
		this.major_no = major_no;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public int getClass_grade() {
		return class_grade;
	}

	public void setClass_grade(int class_grade) {
		this.class_grade = class_grade;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public int getS_time() {
		return s_time;
	}

	public void setS_time(int s_time) {
		this.s_time = s_time;
	}

	public int getE_time() {
		return e_time;
	}

	public void setE_time(int e_time) {
		this.e_time = e_time;
	}

	public int getNow_p() {
		return now_p;
	}

	public void setNow_p(int now_p) {
		this.now_p = now_p;
	}

	public int getMax_p() {
		return max_p;
	}

	public void setMax_p(int max_p) {
		this.max_p = max_p;
	}

	public String getC_plan() {
		return c_plan;
	}

	public void setC_plan(String c_plan) {
		this.c_plan = c_plan;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getProf() {
		return prof;
	}

	public void setProf(String prof) {
		this.prof = prof;
	}

	public List<Integer> getDays() {
		return days;
	}

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "Class1 [class_no=" + class_no + ", ban=" + ban + ", year=" + year + ", term=" + term + ", major_no="
				+ major_no + ", user_no=" + user_no + ", class_name=" + class_name + ", class_grade=" + class_grade
				+ ", credit=" + credit + ", classroom=" + classroom + ", s_time=" + s_time + ", e_time=" + e_time
				+ ", now_p=" + now_p + ", max_p=" + max_p + ", c_plan=" + c_plan + ", file=" + file + ", prof=" + prof
				+ ", days=" + days + "]";
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
