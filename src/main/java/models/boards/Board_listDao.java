package models.boards;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.BoardMapper;

//이동원
public class Board_listDao {
	private Class<BoardMapper> cls = BoardMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public String selectOne(String boardid) {
		SqlSession session = MyBatisConnection.getConnection();
		try { return session.getMapper(cls).selectOne(boardid);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session); }
		return null;
	}
	
}
