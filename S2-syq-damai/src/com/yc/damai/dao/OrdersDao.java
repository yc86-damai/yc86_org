package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

public class OrdersDao {

	public int insert(String uid) {
		String sql = "INSERT INTO dm_orders SELECT\n" + "	NULL,\n" + "	c.total,\n" + "	now(),\n" + " '未支付',\n"
				+ "	a.id,\n" + "	b.id\n" + "FROM\n" + "	dm_user a\n" + "JOIN dm_address b ON a.id = b.uid\n"
				+ "AND dft = 1\n" + "JOIN (\n" + "	SELECT\n" + "		sum(b.shop_price * a.count) total,\n"
				+ "		a.uid\n" + "	FROM\n" + "		dm_cart a\n" + "	JOIN dm_product b ON a.pid = b.id\n"
				+ "	WHERE\n" + "		a.uid = ?\n" + "	GROUP BY\n" + "		a.uid\n" + ") c ON a.id = c.uid\n"
				+ "WHERE\n" + "	a.id = ?";
		return new DBHelper().update(sql, uid, uid);
	}

	public Map<String, Object> queryNewOrders(String uid) {
		String sql = "select a.*,b.addr,b.phone,b.name from dm_orders a join dm_address b on a.aid=b.id "
				+ "where a.uid=? and state=0 order by id desc limit 0,1";
		List<Map<String, Object>> list = new DBHelper().query(sql, uid);

		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public List<Map<String, Object>> query(String ename, String name, String page, String rows) {

		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;

		String sql = "SELECT\n" + "	a.id ,a.total,a.createtime,a.state,b.`name`,b.addr,b.phone,c.ename\n" + "FROM\n"
				+ "	dm_orders a\n" + "JOIN dm_address b ON a.aid = b.id\n"
				+ "JOIN (SELECT * FROM dm_user) c ON c.id = a.uid " + " where 1=1" + where + " limit ?,?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}

	public int count(String ename, String name) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		String sql = "SELECT\n" + "	null\n" + "FROM\n" + "	dm_orders a\n" + "JOIN dm_address b ON a.aid = b.id\n"
				+ "JOIN (SELECT * FROM dm_user) c ON c.id = a.uid  where 1=1" + where;

		return new DBHelper().count(sql, params.toArray());
	}

	public List<Map<String, Object>> query1(String ename, String name, String page, String rows) {

		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;

		String sql = "SELECT\n" + "	a.id ,a.total,a.createtime,a.state,b.`name`,b.addr,b.phone,c.ename\n" + "FROM\n"
				+ "	dm_orders a\n" + "JOIN dm_address b ON a.aid = b.id\n"
				+ "JOIN (SELECT * FROM dm_user) c ON c.id = a.uid " + " where a.state='未发货' and 1=1" + where + " limit ?,?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}

	public int count1(String ename, String name) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		String sql = "SELECT	null FROM	dm_orders a JOIN dm_address b ON a.aid = b.id\n" +
				"				JOIN (SELECT * FROM dm_user) c ON c.id = a.uid  where 1=1 and  a.state=\"未发货\" " + where;

		return new DBHelper().count(sql, params.toArray());
	}
	
	
	public void update(String id) {
		String sql = "update dm_orders set state='已发货' where id=?";
		new DBHelper().update(sql, id);
	}
	
	public List<Map<String, Object>> query2(String ename, String name, String page, String rows) {

		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;

		String sql = "SELECT\n" + "	a.id ,a.total,a.createtime,a.state,b.`name`,b.addr,b.phone,c.ename\n" + "FROM\n"
				+ "	dm_orders a\n" + "JOIN dm_address b ON a.aid = b.id\n"
				+ "JOIN (SELECT * FROM dm_user) c ON c.id = a.uid " + " where a.state='已收货' and 1=1" + where + " limit ?,?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}

	public int count2(String ename, String name) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (ename != null && ename.trim().isEmpty() == false) {
			where += "  and ename like ?";
			params.add("%" + ename + "%");
		}

		if (name != null && name.trim().isEmpty() == false) {
			where += "  and name = ?";
			params.add(name);
		}

		String sql = "SELECT null FROM 	dm_orders a JOIN dm_address b ON a.aid = b.id \n" +
				"				 JOIN (SELECT * FROM dm_user) c ON c.id = a.uid  where 1=1 and  a.state=\"已收货\"" + where;

		return new DBHelper().count(sql, params.toArray());
	}

	public static void main(String[] args) {
		new OrdersDao().insert("2");
		new OrderitemDao().insert("2");
		new CartDao().deleteByUid("2");

	}
}
