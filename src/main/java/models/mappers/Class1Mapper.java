package models.mappers;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import models.classes.Class1;

public interface Class1Mapper {
	@Insert("INSERT INTO classes " + "(class_no, ban, year, term, major_no, user_no, class_name, class_grade, credit, "
			+ " classroom, s_time, e_time, max_p, c_plan, file) " + "VALUES "
			+ "(#{class1.class_no}, #{class1.ban}, #{class1.year}, #{class1.term}, "
			+ " #{class1.major_no}, #{class1.user_no}, #{class1.class_name}, "
			+ "#{class1.class_grade}, #{class1.credit}, #{class1.classroom}, "
			+ "#{class1.s_time}, #{class1.e_time}, #{class1.max_p}, " + "#{class1.c_plan}, #{class1.file})")
	int insert(@Param("class1") Class1 class1);

	@Insert("INSERT INTO class_days " + "(class_no, ban, year, term, days) " + "VALUES "
			+ "(#{class1.class_no}, #{class1.ban}, #{class1.year}, #{class1.term}, #{day})")
	int insertDay(@Param("class1") Class1 class1, @Param("day") Integer day);

	@Select("select * from classes where user_no")
	Class1 selectOne(String user_no);
}
