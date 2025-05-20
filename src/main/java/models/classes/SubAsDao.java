package models.classes;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.SubAsMapper;


public class SubAsDao {
	private Class<SubAsMapper> cls = SubAsMapper.class;
	private Map<String, Object> map = new HashMap<>();
	private int i;
	public int insertScores(int as_no, Map<Student, Integer> scores) {
		i = 0;
		SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 map.put("as_no", as_no);
			 scores.forEach((st, sc) -> {
				 map.put("user_no", st.USER_NO);
				 map.put("as_score", sc);
				 if(conn.getMapper(cls).insertScore(map) == 1) { i++; }
			 });
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return i;
	}
}
