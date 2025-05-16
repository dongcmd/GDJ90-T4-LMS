package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.users.User;

/* model/mapper/UsersMapper.java - 김기흔 */

public interface UserMapper {

	@Insert("insert into member (id,pass,name,gender,tel,email,picture)"
				 + " values (#{id},#{pass},#{name},#{gender},#{tel},#{email},#{picture})")
	int insert(User user);
	
	//로그인
	@Select("select * from users where user_no = #{value}")
	User selectOne(String user_no);
	
	//아이디찾기
	@Select("select user_no from users where user_name=#{user_name} and email=#{email}")
	String userSearch(@Param("user_name") String user_name, @Param("email") String email);
	
	//계정정보 존재확인
	@Select("select user_no from users where user_no=#{value}")
	String isUser(String user_no);
	
	//비밀번호 초기화
	@Update("update users set password=#{randpw} where user_no=#{user_no}")
	int pwReset(@Param("user_no") String user_no, @Param("randpw") String randpw);
	
	//내 정보 수정
	@Update("update users set user_name=#{user_name},gender=#{gender},tel=#{tel},email=#{email},"
				+ "grade=#{grade}, major_no=#{major_no} where user_no=#{user_no}")
	int update(User user);
	
	//비밀번호 재설정
	@Update("update users set password=#{n_pass1} where user_no=#{login}")
	int updatePass(@Param("login") String login, @Param("n_pass1") String n_pass1);

	//모든 정보 불러오기
	@Select("select * from users")
	List<User> getAllUsers();
	
	
	@Delete("delete from member where id=#{id}")
	int delete(String id);
	
	
	@Select("select pass from member where id=#{id} and email=#{email} and tel =#{tel}")
	String pwSearch(String id, String email, String tel);


	@Delete("select * from users where #{type}=#{keyword}")
	List<User> searchUsers(@Param("type") String type, @Param("keyword") String keyword);





	
}
