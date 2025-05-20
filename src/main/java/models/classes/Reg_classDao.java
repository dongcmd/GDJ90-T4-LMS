package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.Reg_classMapper;

public class Reg_classDao {
	private Class<Reg_classMapper> cls = Reg_classMapper.class;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public List<String> students(Reg_class rc) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).students(rc);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}


}
