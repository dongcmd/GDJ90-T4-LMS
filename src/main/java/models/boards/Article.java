package models.boards;

import java.time.LocalDateTime;

//이동원
public class Article {
	private String board_id;
    private String arti_no;
    private String arti_title;
    private String arti_content;
    private String file;
    private LocalDateTime arti_date;
    private String user_no;
    private boolean arti_pinned;
    
	public String getBoard_id() {
		return board_id;
	}
	public void setBoard_id(String board_id) {
		this.board_id = board_id;
	}
	public String getArti_no() {
		return arti_no;
	}
	public void setArti_no(String arti_no) {
		this.arti_no = arti_no;
	}
	public String getarti_title() {
		return arti_title;
	}
	public void setarti_title(String arti_title) {
		this.arti_title = arti_title;
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
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public boolean isArti_pinned() {
		return arti_pinned;
	}
	public void setArti_pinned(boolean arti_pinned) {
		this.arti_pinned = arti_pinned;
	}
}