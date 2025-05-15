package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.users.Users;

/* model/mapper/UsersMapper.java - 김기흔 */

public interface UsersMapper {

	@Insert("insert into member (id,pass,name,gender,tel,email,picture)"
				 + " values (#{id},#{pass},#{name},#{gender},#{tel},#{email},#{picture})")
	int insert(Users user);
	
	//로그인
	@Select("select * from users where user_no = #{value}")
	Users selectOne(String user_no);
	
	//아이디찾기
	@Select("select user_no from users where name=#{name} and email=#{email}")
	String userSearch(@Param("name") String name, @Param("email") String email);
	
	//계정정보 존재확인
	@Select("select user_no from users where user_no=#{value}")
	String isUser(String user_no);
	
	//비밀번호 초기화
	@Update("update users set password=#{randpw} where user_no=#{user_no}")
	int pwReset(@Param("user_no") String user_no, @Param("randpw") String randpw);
	
	//내 정보 수정
	@Update("update users set name=#{name},gender=#{gender},tel=#{tel},email=#{email},"
				+ "grade=#{grade}, major_no=#{major_no} where user_no=#{user_no}")
	int update(Users mem);
	
	@Select("select * from member")
	List<Users> selectList();

	@Delete("delete from member where id=#{id}")
	int delete(String id);


	@Select("select pass from member where id=#{id} and email=#{email} and tel =#{tel}")
	String pwSearch(String id, String email, String tel);

	@Update("update users set password=#{n_pass1} where user_no=#{login}")
	int updatePass(@Param("login") String login, @Param("n_pass1") String n_pass1);

	 @Select({
	        "<script>",
	        "SELECT * FROM member",
	        "WHERE id IN",
	        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
	        "#{id}",
	        "</foreach>",
	        "</script>"
	    })
	List<Users> emailList(Map<String, Object> map);



	
}
