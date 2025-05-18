package models.classes;

import org.apache.ibatis.session.SqlSession;
import models.MyBatisConnection;
import models.mappers.Class1Mapper;

public class Class1Dao {

	public boolean insert(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			int cnt = session.getMapper(Class1Mapper.class).insert(cls);
			if (cnt == 0) {
				session.rollback();
				return false;
			}

			// insertDay(cls, day) 형태로 호출
			Class1Mapper mapper = session.getMapper(Class1Mapper.class);
			for (Integer d : cls.getDays()) {
				mapper.insertDay(cls, d);
			}

			session.commit();
			return true;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return false;
		} finally {
			MyBatisConnection.close(session);
		}
	}
}