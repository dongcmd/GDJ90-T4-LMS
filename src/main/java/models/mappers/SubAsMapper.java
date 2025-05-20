package models.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;


public interface SubAsMapper {

	@Insert("insert into submitted_assignments (user_no, as_no, as,score) "
			+ " values (#{user_no}, #{as_no}, #{as_score})")
	public int insertScore(Map<String, Object> map);
}
