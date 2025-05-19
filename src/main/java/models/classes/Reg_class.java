package models.classes;

public class Reg_class {

	private String user_no;
    private String class_no;
    private String ban;
    private int year;
    private int term;
    private String status;
    private int exam1_score;
    private int exam2_score;
    private int as_tot_score;
    private int att_score;
    private String class_name;
    private String prof;
    private String mark;
    
    public void setKey(Class1 class1) {
    	this.user_no = class1.getUser_no();
    	this.class_no = class1.getClass_no();
    	this.ban = class1.getBan();
    	this.year = class1.getYear();
    }
    
	public String getUser_no() {
	    return user_no;
	}
	public void setUser_no(String user_no) {
	    this.user_no = user_no;
	}
	
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
	
	public String getStatus() {
	    return status;
	}
	public void setStatus(String status) {
	    this.status = status;
	}
	
	public int getExam1_score() {
	    return exam1_score;
	}
	public void setExam1_score(int exam1_score) {
	    this.exam1_score = exam1_score;
	}
	
	public int getExam2_score() {
	    return exam2_score;
	}
	public void setExam2_score(int exam2_score) {
	    this.exam2_score = exam2_score;
	}
	
	public int getAs_tot_score() {
	    return as_tot_score;
	}
	public void setAs_tot_score(int as_tot_score) {
	    this.as_tot_score = as_tot_score;
	}
	
	public int getAtt_score() {
	    return att_score;
	}
	public void setAtt_score(int att_score) {
	    this.att_score = att_score;
	}
	
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getProf() {
		return prof;
	}
	public void setProf(String prof) {
		this.prof = prof;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
