package models.classes;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.Reg_classMapper;

public class Reg_classDao {
	private Class<Reg_classMapper> cls = Reg_classMapper.class;
	private Map<String, Object> map = new HashMap<String, Object>();


}
