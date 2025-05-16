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

	@Select("<script>"
			+ "select a.*, u.user_name from articles a "
			+ " join users u on a.user_no = u.user_no "
			+ " where board_id = #{board_id} "
			+ " <if test='column != null'> " 
			+ " and ${column} like concat('%',#{keyword},'%') "
			+ " </if>"
			+ " order by arti_no desc limit #{start}, #{limit}</script>")
	List<Article> list(Map<String, Object> map);

	@Select("select a.*, u.user_name from articles a "
			+ " join users u on a.user_no = u.user_no "
			+ " where arti_no = #{value}")
	Article selectOne(int arti_no);

}
