package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.Class1Mapper;

public class Class1Dao {
	private Class<Class1Mapper> cls = Class1Mapper.class;
	private Map<String, Object> map = new HashMap<String, Object>();

	public List<Class1> list() {
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
