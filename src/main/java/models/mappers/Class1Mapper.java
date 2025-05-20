package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import models.classes.Class1;

public interface Class1Mapper {

	@Select("SELECT * " + "FROM classes WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	Class1 selectOne(Class1 class1);
	
	@Select("SELECT c.* " + "FROM registered_classes rc JOIN classes c "
			+ "ON rc.class_no = c.class_no AND rc.ban = c.ban AND rc.year = c.year AND rc.term = c.term "
			+ "WHERE rc.user_no = #{user_no} AND rc.year = #{year} AND rc.term = #{term}")
	List<Class1> nowClassList(Class1 class1);
	
	@Select("SELECT days " + "FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} "
			+ "AND term = #{term}")
	List<Integer> selectDaysByClass(Class1 class1);

	@Select("SELECT * " + "FROM classes " + "WHERE user_no = #{user_no}")
	List<Class1> selectByProfessor(@Param("user_no") String userNo);

	@Insert("INSERT INTO classes " + "(class_no, ban, year, term, major_no, user_no, class_name, class_grade, credit, "
			+ "classroom, s_time, e_time, max_p, c_plan, file) " + "VALUES " + "(#{class_no}, #{ban}, #{year}, #{term}, #{major_no}, #{user_no}, "
			+ "#{class_name}, #{class_grade}, #{credit}, #{classroom}, " + "#{s_time}, #{e_time}, #{max_p}, #{c_plan}, #{file})")

	int insertClass(Class1 class1);

	@Insert("INSERT INTO class_days " + "(class_no, ban, year, term, days) " + "VALUES "
			+ "(#{class1.class_no}, #{class1.ban}, #{class1.year}, #{class1.term}, #{day})")
	int insertDay(@Param("class1") Class1 class1, @Param("day") Integer day);

	@Update("UPDATE classes SET " + "class_name = #{class_name}, credit = #{credit}, classroom = #{classroom}, "
			+ "s_time = #{s_time}, e_time = #{e_time}, c_plan = #{c_plan}, file = #{file}, max_p = #{max_p} " + "WHERE class_no = #{class_no} "
			+ "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	int updateClass(Class1 class1);

	@Delete("DELETE FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	int deleteDays(Class1 class1);

	@Delete("DELETE FROM classes " + "WHERE class_no = #{class_no} " + "  AND ban = #{ban} " + "  AND year = #{year} " + "  AND term = #{term}")
	int deleteClass(Class1 class1);
	
	@Delete("DELETE FROM registered_classes WHERE user_no = #{user_no} AND class_no = #{class_no} AND ban = #{ban} AND year = #{year} AND term = #{term}")
	int cancelClass(Class1 class1);
	
	@Select("SELECT * " + "FROM classes WHERE year = #{class1.year} " + "AND term = #{class1.term} " + "AND class_no " + "NOT IN (SELECT class_no "
			+ "FROM registered_classes " + "WHERE user_no = #{user_no}) ")
	List<Class1> selectClassesByYearTerm(@Param("class1") Class1 cls, @Param("user_no") String user_no);

	@Select("SELECT COUNT(*) " + "FROM registered_classes " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} "
			+ "AND term = #{term}")
	int enrolledCount(Class1 class1);

	@Insert("INSERT INTO registered_classes(user_no, class_no, ban, year, term, status) "
			+ "VALUES (#{user_no}, #{class_no}, #{ban}, #{year}, #{term}, 0)")
	boolean insertRegisteredClass(Class1 cls);
	
}