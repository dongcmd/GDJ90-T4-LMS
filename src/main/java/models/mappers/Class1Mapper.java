package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.classes.Class1;


public interface Class1Mapper {
	@Select("select * from classes")
	List<Class1> selectList();

	@Select("select * from classes where user_no")
	Class1 selectOne(String user_no);
}
