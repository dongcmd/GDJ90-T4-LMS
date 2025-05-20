package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.others.Notification;

public interface NotificationMapper {

	@Select("select * from notifications "
			+ " where user_no = #{user_no} "
			+ " order by is_pinned DESC, notif_date DESC")
	List<Notification> getNotificationsByUser(String user_no);
	


}
