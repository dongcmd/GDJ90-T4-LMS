package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.classes.Reg_class;

public interface Reg_classMapper {

	@Select("select user_no from registered_classes "
		+ " where class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term}")
	List<String> students(Reg_class rc);

}
