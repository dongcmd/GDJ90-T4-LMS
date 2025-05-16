package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.Class1;

public interface Class1Mapper {
	@Select("selet * from classes")
	List<Class1> selectList();
}
