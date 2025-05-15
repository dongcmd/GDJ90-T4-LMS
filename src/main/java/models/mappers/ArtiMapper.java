package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import models.boards.Article;

public interface ArtiMapper {
	
	@Select("<script>select count(arti_no) from articles "
			+ " where board_id = #{board_id} "
			+ " <if test='column != null'> " 
			+ " and ${column} like concat('%',#{keyword},'%') "
			+ " </if></script>")
	int count(Map<String, Object> map);

	@Select("<script>select * from articles "
			+ " where board_id = #{board_id} "
			+ " <if test='column != null'> " 
			+ " and ${column} like concat('%',#{keyword},'%') "
			+ " </if>"
			+ " order by arti_no desc</script>")
	List<Article> list(Map<String, Object> map);

}
