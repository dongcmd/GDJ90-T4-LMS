package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.classes.Class1;


public interface Class1Mapper {
	@Select("selects * from classes")
	List<Class1> selectList();
}
