package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

public class PayDao {
	
	public Map<String, Object> selectPay(String id, String payword) {
		String sql = "select * from dm_user where id=? and payword=?";
		DBHelper dbh = new DBHelper();

		List<Map<String, Object>> list = dbh.query(sql, id, payword);

		if (list.size() == 0) {
			return null;
		} else {
			Map<String, Object> user = list.get(0);
			return user;
		}

	}
	
	
	public List<Map<String,Object>> query(String oid){
		String sql ="SELECT\n" +
				"	a.oid,b.image,b.pname,a.total,a.count,c.total,d.addr,d.phone,d.name\n" +
				"FROM\n" +
				"	dm_orderitem a\n" +
				"JOIN dm_product b ON a.pid = b.id\n" +
				"JOIN (SELECT * FROM dm_orders) c ON c.id = a.oid\n" +
				"join (SELECT * from dm_address) d on c.aid = d.id where a.oid=?";
		
		return new DBHelper().query(sql,oid);
	}
	
	public Map<String, Object> queryNewOrders(String id) {
		String sql = "select a.*,b.addr,b.phone,b.name from dm_orders a join dm_address b on a.aid=b.id where a.id=?";
		List<Map<String, Object>> list = new DBHelper().query(sql, id);

		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public List<?> queryByOid(String oid) {
		String sql = "select * from dm_orderitem a join dm_product b on a.pid=b.id where oid=?";
		return new DBHelper().query(sql, oid);
	}
	
	
	public void updateByOid(String oid) {
		String sql="update dm_orders set state='未发货',createtime = now() where id=?";
		new DBHelper().update(sql, oid);
	}

}
