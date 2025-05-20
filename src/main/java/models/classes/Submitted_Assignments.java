package models.classes;

import java.io.File;
import java.util.Arrays;

public class Submitted_Assignments {
	private String user_no;
	private int as_no;
	private String file;
	private int as_score;
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public int getAs_no() {
		return as_no;
	}
	public void setAs_no(int as_no) {
		this.as_no = as_no;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getAs_score() {
		return as_score;
	}
	public void setAs_score(int as_score) {
		this.as_score = as_score;
	}
	@Override
	public String toString() {
		return "Submitted_Assignments [user_no=" + user_no + ", as_no=" + as_no + ", file=" + file + ", as_score="
				+ as_score + "]";
	}
}
