package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

public class EvaluateDao {
	
	public int insert(String content,String image,String pid,String uid) {
		String sql = "insert into dm_evaluate values(null,?,?,?,?,now())";
		return new DBHelper().update(sql,content,image,pid,uid);
	}
	
	public List<Map<String,Object>> query(String pid){
		String sql ="select * from dm_evaluate a join dm_user b on a.uid=b.id where a.pid=?";
		
		return new DBHelper().query(sql,pid);
	}

}
