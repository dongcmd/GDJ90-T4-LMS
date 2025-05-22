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
import models.classes.Sub_as;
import models.classes.Submitted_Assignments;


public interface AsMapper {
	
	// 과제 추가=================
	@Insert("INSERT INTO assignments "
			+ "(as_name, as_content, as_s_date, as_e_date, as_point, class_no, ban, year, term) " +
	        "VALUES (#{as_name}, #{as_content}, #{as_s_date}, #{as_e_date}, #{as_point}, #{class_no}, #{ban}, #{year}, #{term})")
	int insert(Assignment as);

	@Select("select a.*,count(sa.user_no) from assignments a join submitted_assignments sa on a.as_no = sa.as_no "
			+ " where a.class_no = #{class_no} and a.ban = #{ban} and a.year = #{year} and a.term = #{term}")
	List<Assignment> list(Class1 class1);

	@Select("select * from assignments where as_no = #{value}")
	Assignment selectOne(int as_no);

	@Update("update assignments set as_name=#{as_name}, as_content=#{as_content}, as_s_date=#{as_s_date}, as_e_date=#{as_e_date},"
			+ " as_point=#{as_point}, class_no=#{class_no}, ban = #{ban}, year=#{year}, term=#{term} where as_no=#{as_no}")
	int update(Assignment as);

	@Delete("delete from assignments where as_no=#{value}")
	int deleteuser(int as_no);

	
	//제출 과제 선택 ==============================================
	@Select("select * from submitted_assignments where user_no=#{user_no} and as_no = #{as_no}")
	Sub_as selectSub_as(@Param("user_no") String user_no, @Param("as_no") int as_no);
	
	//과제 제출/수정
	@Update("UPDATE submitted_assignments SET file = #{file} WHERE user_no = #{user_no} AND as_no = #{as_no}")
	int updateAs(Sub_as as);
	
	//전체 과제 선택
	@Select("select * from assignments ")
	List<Assignment> selectAll();
	
	//해당 수업의 과제 리스트 불러오기
	@Select("select * from assignments where class_no=#{class_no} and ban=#{ban} and year=#{year} and term=#{term}")
	List<Assignment> selectAsByClass(Class1 loginclass);

	//수업의 수강생 user_no 리스트 불러오기
	@Select("select user_no from registered_classes where class_no=#{value}")
	List<String> selectReg_Std(String class_no);

	//수업의 과제번호 리스트 불러오기
	@Select("select as_no from assignments where class_no=#{class_no} and ban=#{ban} and year=#{year} and term=#{term}")
	List<Integer> selectAs_no(Class1 class1);

	//user_no, as_no에 맞는 File 속성값 갖고오기
	@Select("select file from submitted_assignments where user_no=#{user_no} and as_no=#{as_no}")
	String selectFile(@Param("user_no") String user_no, @Param("as_no") int as_no);

	
}
