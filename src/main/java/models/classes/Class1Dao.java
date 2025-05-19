package models.classes;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import models.MyBatisConnection;
import models.mappers.Class1Mapper;

public class Class1Dao {

	public boolean insert(Class1 cls) {
		try (SqlSession session = MyBatisConnection.getConnection()) {
			Class1Mapper m = session.getMapper(Class1Mapper.class);
			if (m.insert(cls) == 0) {
				session.rollback();
				return false;
			}
			// days 일괄 삽입이 아니라 단건 삽입 메서드라면 기존 루프 유지
			for (Integer d : cls.getDays()) {
				m.insertDay(cls, d);
			}
			session.commit();
			return true;
		}
	}

	public List<Class1> selectByProfessor(String userNo) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper mapper = session.getMapper(Class1Mapper.class);
			List<Class1> list = mapper.selectByProfessor(userNo);

			for (Class1 cls : list) {
				List<Integer> days = mapper.selectDaysByClass(cls);
				cls.setDays(days);
			}
			return list;
		} finally {
			MyBatisConnection.close(session);
		}
	}
	public Class1 selectOne(Class1 key) {
        try (SqlSession session = MyBatisConnection.getConnection()) {
            Class1Mapper m = session.getMapper(Class1Mapper.class);
            Class1 cls = m.selectOne(key);
            if (cls != null) {
                cls.setDays(m.selectDaysByClass(key));
            }
            return cls;
        }
    }
	public boolean update(Class1 cls) {
        try (SqlSession session = MyBatisConnection.getConnection()) {
            Class1Mapper m = session.getMapper(Class1Mapper.class);
            if (m.updateClass(cls) == 0) {
                session.rollback();
                return false;
            }
            m.deleteDays(cls);
            for (Integer d : cls.getDays()) {
                m.insertDay(cls, d);
            }
            session.commit();
            return true;
        }
    }
	public boolean delete(Class1 cls) {
	    try (SqlSession session = MyBatisConnection.getConnection()) {
	        Class1Mapper m = session.getMapper(Class1Mapper.class);
	        m.deleteDays(cls);
	        if (m.deleteClass(cls) == 0) {
	            session.rollback();
	            return false;
	        }
	        session.commit();
	        return true;
	    }
	}
}