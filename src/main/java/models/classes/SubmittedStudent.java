package models.classes;
// 이동원
public class SubmittedStudent {
	private String user_no; // 학번
	private String user_grade; // 학년
	private String user_name; // 이름
	private String as_file; // 파일 경로
	private boolean submitted = false; // 과제 제출 여부
	
	public SubmittedStudent() { }
	public SubmittedStudent(String user_no, String user_grade, String user_name, String as_file) {
		this.user_no = user_no;
		this.user_grade = user_grade;
		this.user_name = user_name;
		this.as_file = as_file;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getAs_file() {
		return as_file;
	}
	public void setAs_file(String as_file) {
		this.as_file = as_file;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	
	
}
