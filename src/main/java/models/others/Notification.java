package models.others;

import java.util.Date;

public class Notification {
    private String notif_no;  // 알림번호
    private String user_no; // 유저번호
    private String notif_content; // 알림내용
    private Date notif_date_; // 알림 작성시간
    private Boolean is_pinned; // 알림 고정
    private Boolean is_read; // 알림 여부
    
	public String getNotif_no() {
		return notif_no;
	}
	public void setNotif_no(String notif_no) {
		this.notif_no = notif_no;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getNotif_content() {
		return notif_content;
	}
	public void setNotif_content(String notif_content) {
		this.notif_content = notif_content;
	}
	public Date getNotif_date_() {
		return notif_date_;
	}
	public void setNotif_date_(Date notif_date_) {
		this.notif_date_ = notif_date_;
	}
	public Boolean getIs_pinned() {
		return is_pinned;
	}
	public void setIs_pinned(Boolean is_pinned) {
		this.is_pinned = is_pinned;
	}
	public Boolean getIs_read() {
		return is_read;
	}
	public void setIs_read(Boolean is_read) {
		this.is_read = is_read;
	}
}
