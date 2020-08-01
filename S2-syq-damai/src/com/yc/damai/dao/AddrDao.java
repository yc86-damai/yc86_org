package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmAddress;
import com.yc.damai.util.DBHelper;

public class AddrDao {
	
	public List<Map<String,Object>> query(String uid){
		String sql ="select * from dm_address where uid=?";
		
		return new DBHelper().query(sql,uid);
	}
	
	public void updateByUid(String uid) {
		String sql="update dm_address set dft=0 where dft=1 and uid=?";
		new DBHelper().update(sql, uid);
	}
	
	public void updateByAddrId(String id) {
		String sql="update dm_address set xz=1 where id=?";
		new DBHelper().update(sql, id);
	}
	
	public void updateByAddrUid(String uid) {
		String sql="update dm_address set xz=0 where xz=1 and uid=?";
		new DBHelper().update(sql, uid);
	}
	
	public void updateById(String id) {
		String sql="update dm_address set dft=1 where id=?";
		new DBHelper().update(sql, id);
	}
	
	public void insert(String uid,DmAddress da) {
		String sql ="insert into dm_address values(null,?,?,?,?,?,now())";
		new DBHelper().update(sql
				,uid
				,da.getAddr()
				,da.getPhone()
				,da.getName()
				,da.getDft());
	}
	
	public void update(String uid,DmAddress da) {
		String sql = "update dm_address set "
				+ "uid=?,"
				+ "addr=?,"
				+ "phone=?,"
				+ "name=?,"
				+ "dft=?,"
				+ "createtime = now()"
				+ " where id=?";
		new DBHelper().update(sql
				,uid
				,da.getAddr()
				,da.getPhone()
				,da.getName()
				,da.getDft()
				,da.getId());
	}

}
