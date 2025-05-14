package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import models.boards.Article;

public interface ArtiMapper {
	
	@Select("select count(arti_no) from articles "
			+ " where boardid = #{boardid} "
			+ " and ${column} like #{keyword}")
	int count(Map<String, Object> map);

	@Select("select * from articles "
			+ " where boardid = #{boardid} "
			+ " and ${column} like #{keyword} "
			+ " order by arti_no")
	List<Article> list(Map<String, Object> map);

}
