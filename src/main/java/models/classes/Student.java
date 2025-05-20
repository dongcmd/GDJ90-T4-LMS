package models.classes;

import models.users.User;

public class Student {
	public final String USER_NO;
	public final String USER_NAME;
	public final int USER_GRADE;
	int exam1_score; // 중간 점수
	int exam2_score; // 기말 점수
	int as_tot_score; // 과제 점수
	int att_score; // 출석 점수
	
	public Student(User user) {
		this.USER_NO = user.getUser_no();
		this.USER_NAME = user.getUser_name();
		this.USER_GRADE = user.getUser_grade();
	}
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Student)) return false;
	    Student other = (Student) o;
	    return USER_NO.equals(other.USER_NO);
	}

	@Override
	public int hashCode() {
	    return USER_NO.hashCode();
	}
}
