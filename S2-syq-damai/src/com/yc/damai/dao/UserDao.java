package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmUser;
import com.yc.damai.util.DBHelper;

public class UserDao {

	public Map<String, Object> selectByLogin(String cname, String password) {
		String sql = "select * from dm_user where ename=? and password=?";
		DBHelper dbh = new DBHelper();

		List<Map<String, Object>> list = dbh.query(sql, cname, password);

		if (list.size() == 0) {
			return null;
		} else {
			Map<String, Object> user = list.get(0);
			return user;
		}

	}
	
	public Map<String, Object> selectAddrByLogin(String username, String password) {
		String sql = "select * from dm_adminuser where username=? and password=?";
		DBHelper dbh = new DBHelper();

		List<Map<String, Object>> list = dbh.query(sql, username, password);

		if (list.size() == 0) {
			return null;
		} else {
			Map<String, Object> user = list.get(0);
			return user;
		}

	}
	
	public Map<String, Object> queryUserById(String id) {
		String sql = "select * from dm_user where id=?";
		return new DBHelper().query(sql, id).get(0);
	}
	
	
	public Map<String, Object> querypwd(String id, String password) {
		String sql = "select * from dm_user where id=? and password=?";
		DBHelper dbh = new DBHelper();
		List<Map<String, Object>> list = dbh.query(sql, id, password);
		if (list.size() == 0) {
			return null;
		} else {
			Map<String, Object> user = list.get(0);
			return user;
		}
	}
	
	public void updatepwd(String id,String password1) {
		String sql="update dm_user set password=? where id= ?";
		new DBHelper().update(sql, password1 , id);
	}
	

	public int insert(DmUser user) {
		String sql = "insert into dm_user values(null,?,?,?,?,?,?,?,now(),?)";
		return new DBHelper().update(sql, user.getEname(), user.getCname(), user.getPassword(), user.getEmail(),
				user.getPhone(), user.getSex(), user.getState(),user.getPayword());
	}
	
	public void update(DmUser user) {
		String sql = "UPDATE dm_user\n" +
				"SET PASSWORD = ?\n" +
				"WHERE\n" +
				"	ename = ?\n" +
				"AND email = ?\n" +
				"AND phone = ?";
		new DBHelper().update(sql
				,user.getPassword()
				,user.getEname()
				,user.getEmail()
				,user.getPhone()
			);
	}
	
	
	
	public void save(String id, String ename, String cname, String createtime, String email, String phone, String sex) {
		String sql = "update dm_user set ename=?,cname=?,createtime=?,"
				+ "email=?,phone=?,sex=? where id=?";
		new DBHelper().update(sql, ename, cname, createtime, email, phone, sex, id);
	}
	
	

}
