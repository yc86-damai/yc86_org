package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmProduct;
import com.yc.damai.util.DBHelper;

public class ProductDao {
	
	public List<Map<String,Object>> query1(DmProduct dp, String page, String rows){
		
		String where = "";
		List<Object> params = new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}
		
		if(dp.getCid() != null) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}
		
		if(dp.getIsHot() != null) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}
		
		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql = "select a.*, b.cname from dm_product a"
				+ " join dm_category b on a.cid = b.id"
				+ " where 1=1"
				+ where
				+ " limit ?, ?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}
	
	public int count1(DmProduct dp){
		String where = "";
		List<Object> params = new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}
		
		if(dp.getCid() != null) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}
		
		if(dp.getIsHot() != null) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}
		String sql = "select null from dm_product where 1=1" + where;
		return new DBHelper().count(sql, params.toArray());
	}
	
	public List<Map<String,Object>> query(){
		String sql ="select a.id id, a.cname cname,b.cname aname from dm_category a "
				+ "join dm_category b on a.pid=b.id where a.pid != 0";
		
		return new DBHelper().query(sql);
	}
	
	public void insert(DmProduct dp) {
		String sql ="insert into dm_product values(null,?,?,?,?,?,?,now(),?)";
		new DBHelper().update(sql 
				,dp.getPname()
				,dp.getMarketPrice()
				,dp.getShopPrice()
				,dp.getImage()
				,dp.getPdesc()
				,dp.getIsHot()
				,dp.getCid());
	}
	
	public void update(DmProduct dp) {
		String sql = "update dm_product set "
				+ "pname=?,"
				+ "market_price=?,"
				+ "shop_price=?,"
				+ "image=?,"
				+ "pdesc=?,"
				+ "is_hot = ?,"
				+ "cid = ?"
				+ " where id=?";
		new DBHelper().update(sql
				,dp.getPname()
				,dp.getMarketPrice()
				,dp.getShopPrice()
				,dp.getImage()
				,dp.getPdesc()
				,dp.getIsHot()
				,dp.getCid()
				,dp.getId());
	}
	
	public int deleteById(String id) {
		String sql ="delete from dm_product where id=?";
		return new DBHelper().update(sql, id);
	}

}