package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import models.classes;

public interface ClassesMapper {
	@Select("selet * from classes")
	List<classes> selectList();
}
