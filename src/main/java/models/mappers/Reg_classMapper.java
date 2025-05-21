package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.classes.Class1;
import models.classes.Reg_class;
import models.classes.Student;

public interface Reg_classMapper {

	@Select("select count(user_no) from registered_classes where "
		+ " class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term}")
	int studentCount(Class1 class1);

	@Select("select u.user_no, u.user_name, u.user_grade, rc.exam1_score, rc.exam2_score, rc.as_tot_score, rc.att_score "
			+ " from users u join registered_classes rc "
			+ " on u.user_no = rc.user_no where (class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term})")
	List<Student> studentList(Class1 class1);

}
