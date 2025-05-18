package models.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.EventMapper;

public class EventDao {
	
	private Class<EventMapper> cls = EventMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	// 학사일정 등록
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
	// 학사일정 업데이트
	public boolean update(Event event) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(event) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 학사일정 삭제
	public boolean delete(int event_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(event_no) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 학사일정 리스트
	public List<Event> evnetList() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).evnetList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	//학사일정 번호
	public Event selectByNo(int event_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectByNo(event_no);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	

}
