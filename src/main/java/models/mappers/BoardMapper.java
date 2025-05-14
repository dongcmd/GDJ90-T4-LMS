package models.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface BoardMapper {
	
	@Select("select boardname from board where ${v}")
	String selectOne(String boardid);

	
}
