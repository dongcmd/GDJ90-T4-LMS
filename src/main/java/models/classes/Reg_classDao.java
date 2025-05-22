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
	
	public int studentCount(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).studentCount(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return -1;
	}

	public List<Student> studentList(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).studentList(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}

	// 
	public boolean updateExamScore(Class1 class1, List<Student> studentList, Map<String, Integer[]> exMap) {
		SqlSession session = MyBatisConnection.getConnection();
		map.clear();
		map.put("class_no", class1.getClass_no());
		map.put("ban", class1.getBan());
		map.put("year", class1.getYear());
		map.put("term", class1.getTerm());
		 try {
			 for(Student st : studentList) {
				 map.put("user_no", st.getUser_no());
				 map.put("exam1", exMap.get(st.getUser_no())[0]);
				 map.put("exam2", exMap.get(st.getUser_no())[1]);
				 System.out.println(session.getMapper(cls).updateExamScore(map));
				 System.out.println(st);
				 System.out.println(exMap.get(st.getUser_no())[0]);
				 System.out.println(exMap.get(st.getUser_no())[1]);
			 }
			 return true;
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		return false;
	}
	

}
