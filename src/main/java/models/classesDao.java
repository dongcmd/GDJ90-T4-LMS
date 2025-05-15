package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.mappers.ClassesMapper;

public class classesDao {
	private Class<ClassesMapper> cls = ClassesMapper.class;
	private Map<String, Object> map = new HashMap<String, Object>();

	public List<classes> list() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
}
