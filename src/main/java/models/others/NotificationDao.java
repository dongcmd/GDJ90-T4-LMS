package models.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.NotificationMapper;

public class NotificationDao {
	private Class<NotificationMapper> cls = NotificationMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	// 알림 리스트
	public List<Notification> notifList() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			System.out.println("테스트");
			return session.getMapper(cls).notifList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
}
