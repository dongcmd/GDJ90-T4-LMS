package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import models.classes.Sub_as;

public interface SubAsMapper {

	@Insert("insert into submitted_assignments (user_no, as_no, as,score) "
		+ " values (#{user_no}, #{as_no}, #{as_score})")
	public int insertScore(Map<String, Object> map);

	@Select("select u.user_no, u.user_grade, u.user_name, sa.as_no as_no, sa.file , sa.as_score as_score from users u "
		+ " join submitted_assignments sa on u.user_no = sa.user_no where as_no = #{as_no}")
	public List<Sub_as> list(int as_no);

	@Select("select u.user_no, u.user_grade, u.user_name, sa.as_no as_no, sa.file as_file, sa.as_score as_score from users u "
			+ " join submitted_assignments sa on u.user_no = sa.user_no where as_no = #{as_no} and u.user_no = #{user_no}")
	public Sub_as selectOne(Map<String, Object> map);
	
}
