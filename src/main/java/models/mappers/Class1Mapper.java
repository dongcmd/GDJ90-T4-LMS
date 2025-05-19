package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import models.classes.Class1;

public interface Class1Mapper {

	@Select("SELECT class_no, ban, year, term, major_no, user_no, class_name, class_grade, "
			+ "credit, classroom, s_time, e_time, max_p, c_plan, file " + "FROM classes "
			+ "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	Class1 selectOne(Class1 class1);

	@Select("SELECT days " + "FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} "
			+ "AND year = #{year} " + "AND term = #{term}")
	List<Integer> selectDaysByClass(Class1 class1);

	@Select("SELECT class_no, ban, year, term, major_no, user_no, class_name, class_grade, "
			+ "credit, classroom, s_time, e_time " + "FROM classes " + "WHERE user_no = #{user_no}")
	List<Class1> selectByProfessor(@Param("user_no") String userNo);

	@Insert("INSERT INTO classes " + "(class_no, ban, year, term, major_no, user_no, class_name, class_grade, credit, "
			+ "classroom, s_time, e_time, max_p, c_plan, file) " + "VALUES "
			+ "(#{class_no}, #{ban}, #{year}, #{term}, #{major_no}, #{user_no}, "
			+ "#{class_name}, #{class_grade}, #{credit}, #{classroom}, "
			+ "#{s_time}, #{e_time}, #{max_p}, #{c_plan}, #{file})")
	int insert(Class1 class1);

	@Insert("INSERT INTO class_days " + "(class_no, ban, year, term, days) " + "VALUES "
			+ "(#{class1.class_no}, #{class1.ban}, #{class1.year}, #{class1.term}, #{day})")
	int insertDay(@Param("class1") Class1 class1, @Param("day") Integer day);

	@Update("UPDATE classes SET " + "class_name = #{class_name}, credit = #{credit}, classroom = #{classroom}, "
			+ "s_time = #{s_time}, e_time = #{e_time}, c_plan = #{c_plan}, file = #{file}, max_p = #{max_p} "
			+ "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	int updateClass(Class1 class1);

	@Delete("DELETE FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} "
			+ "AND term = #{term}")
	int deleteDays(Class1 class1);

	@Delete("DELETE FROM classes " + "WHERE class_no = #{class_no} " + "  AND ban = #{ban} " + "  AND year = #{year} "
			+ "  AND term = #{term}")
	int deleteClass(Class1 class1);
}