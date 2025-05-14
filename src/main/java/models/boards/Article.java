package models.boards;

import java.time.LocalDateTime;

//이동원
public class Article {
	private int boardid;
    private int arti_no;
    private String title;
    private String arti_content;
    private String file;
    private LocalDateTime arti_date;
    private int user_no;
    private boolean arti_pinned;
    
	public int getBoardid() {
		return boardid;
	}
	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}
	public int getArti_no() {
		return arti_no;
	}
	public void setArti_no(int arti_no) {
		this.arti_no = arti_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArti_content() {
		return arti_content;
	}
	public void setArti_content(String arti_content) {
		this.arti_content = arti_content;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public LocalDateTime getArti_date() {
		return arti_date;
	}
	public void setArti_date(LocalDateTime arti_date) {
		this.arti_date = arti_date;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public boolean isArti_pinned() {
		return arti_pinned;
	}
	public void setArti_pinned(boolean arti_pinned) {
		this.arti_pinned = arti_pinned;
	}
}