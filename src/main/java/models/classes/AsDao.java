package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import models.MyBatisConnection;
import models.mappers.AsMapper;



public class AsDao {
	 private Class<AsMapper> cls = AsMapper.class;
	 private Map<String, Object> map = new HashMap<>();
	
	 // 과제 추가==============================================
	 public boolean insert(Assignment as) {
		 SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 if(conn.getMapper(cls).insert(as) > 0) return true;
			 else return false;
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return false;
	}
	// 과제 전체 목록 조회==========================================
	public List<Assignment> list(Class1 class1) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).list(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	//과제 선택=============================================
	public Assignment selectOne(int as_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectOne(as_no);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	// 과제 수정=============================================
	public boolean update(Assignment as) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(as) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 과제 삭제 ============================================
	public boolean deleteAssignment(int as_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).deleteuser(as_no) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// 학생별 점수 ============================
	public Map<Student, Integer> scores(Assignment as, Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Map<Student, Integer> result = new HashMap<>();
			List<Student> stList = new Reg_classDao().studentList(class1); 
			Map<String, Object> map = new HashMap<>();
			for(Student st : stList) {
				map.put("user_no", st.getUser_no());
				map.put("as_no", as.getAs_no());
				result.put(st, session.getMapper(cls).scores(map));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean insertAs(Submitted_Assignments as) {
		 SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 if(conn.getMapper(cls).insertAs(as) > 0) return true;
			 else return false;
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return false;
	}
	



}
