package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;


public class OlistDao {
	
	public List<Map<String,Object>> query(String uid){
		String sql ="SELECT\n" +
				"	c.id,\n" +
				"	a.pid,\n" +
				"	a.count,\n" +
				"	c.total,\n" +
				"	b.image,\n" +
				"	b.pname,\n" +
				"	b.shop_price,\n" +
				"	c.state\n" +
				"FROM\n" +
				"	dm_orderitem a\n" +
				"JOIN dm_product b ON a.pid = b.id\n" +
				"JOIN (SELECT * FROM dm_orders) c ON c.id = a.oid\n" +
				"WHERE\n" +
				"	c.uid =?";
		
		return new DBHelper().query(sql,uid);
	}
	
	public List<Map<String,Object>> queryById(String id){
		String sql ="select state from dm_orders where id=?";

		return new DBHelper().query(sql,id);
	}
	
	public void updateById(String id) {
		String sql="update dm_orders set state='已收货' where id=?";
		new DBHelper().update(sql, id);
	}

}
