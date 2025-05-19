package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.others.Notification;

public interface NotificationMapper {
	
	@Select("select * from notifications order by notif_no desc")
	List<Notification> notifList();

}
