package models.mappers;

import org.apache.ibatis.annotations.Insert;

import models.others.Event;

public interface EventMapper {

	@Insert("")
	int insert(Event event);

}
