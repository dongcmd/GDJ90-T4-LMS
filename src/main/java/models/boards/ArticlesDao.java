package models.boards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.ArtiMapper;
import models.mappers.BoardMapper;

//이동원
public class ArticlesDao {
	private Class<ArtiMapper> cls = ArtiMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public int count(String boardid, String column, String keyword) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("boardid", boardid);
			map.put("column", column);
			map.put("keyword", keyword);
			return session.getMapper(cls).count(map);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return 0;
	}

	public List<Article> list(String boardid, int pageNum, int limit, String column, String keyword) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("boardid", boardid);
			map.put("start", (pageNum-1) * limit);
			map.put("limit", limit);
			map.put("column", column);
			map.put("keyword", keyword);
			return session.getMapper(cls).list(map);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
}
