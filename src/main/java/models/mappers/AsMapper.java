package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.classes.Assignment;
import models.classes.Class1;

public interface AsMapper {
	
	// 과제 추가=================
	@Insert("INSERT INTO assignments "
			+ "(as_name, as_content, as_s_date, as_e_date, as_point, class_no, ban, year, term) " +
	        "VALUES (#{as_name}, #{as_content}, #{as_s_date}, #{as_e_date}, #{as_point}, #{class_no}, #{ban}, #{year}, #{term})")
	int insert(Assignment as);

	@Select("select * from assignments where class_no = #{class_no} "
		+ " and ban = #{ban} and year = #{year} and term = #{term}")
	List<Assignment> list(Class1 class1);

	@Select("select * from assignments where as_no = #{value}")
	Assignment selectOne(int as_no);

	@Update("update assignments set as_name=#{as_name}, as_content=#{as_content}, as_s_date=#{as_s_date}, as_e_date=#{as_e_date},"
			+ " as_point=#{as_point}, class_no=#{class_no}, ban = #{ban}, year=#{year}, term=#{term} where as_no=#{as_no}")
	int update(Assignment as);

	
	@Delete("delete from assignments where as_no=#{value}")
	int deleteuser(int as_no);
	
}
