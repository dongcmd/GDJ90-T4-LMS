package models.others;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.EventMapper;

public class EventDao {
	
	private Class<EventMapper> cls = EventMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public boolean insert(Event event) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(event) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	
	

}
