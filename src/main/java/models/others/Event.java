package models.others;

import java.time.LocalDateTime;

// 원동인
public class Event {
    private int event_no;  // 행사번호
    private String event_name; // 행사명
    private LocalDateTime even_s_date; // 사작일
    private LocalDateTime even_e_date; // 종료일
    
	public int getEvent_no() {
		return event_no;
	}
	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public LocalDateTime getEven_s_date() {
		return even_s_date;
	}
	public void setEven_s_date(LocalDateTime even_s_date) {
		this.even_s_date = even_s_date;
	}
	public LocalDateTime getEven_e_date() {
		return even_e_date;
	}
	public void setEven_e_date(LocalDateTime even_e_date) {
		this.even_e_date = even_e_date;
	}
}
