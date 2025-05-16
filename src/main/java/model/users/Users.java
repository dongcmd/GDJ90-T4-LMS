package model.users;

// 사용자 정보 테이블(Users)의 Bean Class

public class Users {
	private String user_no;
	private String password;
	private String role;
	private String email;
	private String tel;
	private String name;
	private int gender;
	private int grade;
	private String major_no;
	
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getMajor_no() {
		return major_no;
	}
	public void setMajor_no(String major_no) {
		this.major_no = major_no;
	}
	@Override
	public String toString() {
		return "Users [user_no=" + user_no + ", password=" + password + ", role=" + role + ", email=" + email + ", tel="
				+ tel + ", name=" + name + ", gender=" + gender + ", grade=" + grade + ", major_no=" + major_no + "]";
	}
	
	


}
