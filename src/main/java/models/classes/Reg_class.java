package models.classes;

public class Reg_class {

    private String userNo;
    private String classNo;
    private String ban;
    private int year;
    private int term;
    private String status;
    private Integer exam1Score;
    private Integer exam2Score;
    private Integer asTotScore;
    private Integer attScore;
    private Integer totScore;
    private String class_name;
    
    public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
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

    public Integer getExam1Score() {
        return exam1Score;
    }

    public void setExam1Score(Integer exam1Score) {
        this.exam1Score = exam1Score;
    }

    public Integer getExam2Score() {
        return exam2Score;
    }

    public void setExam2Score(Integer exam2Score) {
        this.exam2Score = exam2Score;
    }

    public Integer getAsTotScore() {
        return asTotScore;
    }

    public void setAsTotScore(Integer asTotScore) {
        this.asTotScore = asTotScore;
    }

    public Integer getAttScore() {
        return attScore;
    }

    public void setAttScore(Integer attScore) {
        this.attScore = attScore;
    }

    public Integer getTotScore() {
        return totScore;
    }

    public void setTotScore(Integer totScore) {
        this.totScore = totScore;
    }
}
