package models.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.NotificationMapper;
import models.users.User;

public class NotificationDao {
	private Class<NotificationMapper> cls = NotificationMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	// 알림 리스트
	public List<Notification> getNotificationsByUser(String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).getNotificationsByUser(user_no);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	



}
