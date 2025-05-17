package models.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.UserMapper;

/* model/users/UsersDao.java - 김기흔 */

public class UserDao {
	 private Class<UserMapper> cls = UserMapper.class;
	 private Map<String, Object> map = new HashMap<>();
	 public boolean insert(User mem) {
		 SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 //executeUpdate() : 실행 후 변경된 레코드의 갯수 리턴 
			 if(conn.getMapper(cls).insert(mem) > 0) return true;
			 else return false;
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return false;
	 }
	 //로그인정보 부르기
	 public User selectOne(String user_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectOne(user_no);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;		 
	 }
	 //아이디 잧기
	 public String userSearch(String user_name, String email) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).userSearch(user_name, email);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null; 
		 
	 }
	 //있는 계정인지 확인
		public String isUser(String user_no) {
			 SqlSession session = MyBatisConnection.getConnection();
			 try {
				 return session.getMapper(cls).isUser(user_no);
			 } catch (Exception e) {
				 e.printStackTrace();
			 } finally {
				 MyBatisConnection.close(session);
			 }
			return null;
		}
	  // 비밀번호 초기화
		public int pwReset(String user_no, String randpw) {
			 SqlSession session = MyBatisConnection.getConnection();
			 try {
				 return session.getMapper(cls).pwReset(user_no, randpw);
			 } catch (Exception e) {
				 e.printStackTrace();
			 } finally {
				 MyBatisConnection.close(session);
			 }
			return 0;
		}
	    //내 정보 수정
		public boolean update(User user) {
			SqlSession session = MyBatisConnection.getConnection();
			try {
				return session.getMapper(cls).update(user) > 0;
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(session);
			}
			return false;
		}
		//유저 검색
		public List<User> searchUsers(String type, String keyword) {
			SqlSession session = MyBatisConnection.getConnection();		
			try {
				return session.getMapper(cls).searchUsers(type, keyword);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(session);
			}
			return null;
		}
		
	 public List<Member> list() {
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
	 
		
	 public boolean delete(String id) {
			SqlSession session = MyBatisConnection.getConnection();
			try {
				return session.getMapper(cls).delete(id) > 0;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MyBatisConnection.close(session);
			}
			return false;
			
	}
	 
	public String pwSearch(String id, String email, String tel) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).pwSearch(id, email, tel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	public boolean updatePass(String login, String n_pass1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).updatePass(login, n_pass1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
		
	}

	// 메일 
	public List<Member> emailList(String[] ids) {
		SqlSession session = MyBatisConnection.getConnection();		
		try {
			map.clear();
			map.put("ids", ids);
			return session.getMapper(cls).emailList(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	public List<User> getAllUsers() {
			 SqlSession session = MyBatisConnection.getConnection();
			 try {
				 return session.getMapper(cls).getAllUsers();
			 } catch (Exception e) {
				 e.printStackTrace();
			 } finally {
				 MyBatisConnection.close(session);
			 }
		return null;
	}
	public boolean pwCheck(String user_no, String password) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 map.clear();
			 map.put("user_no", user_no);
			 map.put("password", password);
			 return session.getMapper(cls).pwCheck(map) > 0;
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
	return false;
	}
}