package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import models.classes.SubmittedStudent;


public interface SubAsMapper {

	@Insert("insert into submitted_assignments (user_no, as_no, as,score) "
		+ " values (#{user_no}, #{as_no}, #{as_score})")
	public int insertScore(Map<String, Object> map);

	@Select("select u.user_no, u.user_grade, u.user_name, as.file as_file from users "
		+ " join submitted_assignments on u.user_no = sa.user_no where as_no = #{as_no}")
	public List<SubmittedStudent> submittedStudents(int as_no);
}
