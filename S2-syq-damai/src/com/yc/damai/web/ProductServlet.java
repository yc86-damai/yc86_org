package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.yc.damai.dao.ProductDao;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;
import com.yc.damai.util.DBHelper;


@WebServlet("/product.do")
public class ProductServlet extends BaseServlet  {
	private static final long serialVersionUID = 1L;
	
	private ProductDao pdao = new ProductDao();
	
	protected void queryhot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from dm_product where is_hot=1 order by id desc limit 0,10";
		List<?> list = new DBHelper().query(sql);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print(response,page);
	}

	
	protected void querynew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from dm_product order by createtime desc limit 0,10";
		List<?> list = new DBHelper().query(sql);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
	
	
	protected void mulu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from dm_category where id<=7";
		List<?> list = new DBHelper().query(sql);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
	
	
	// 查询某件商品
	protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql = "select * from dm_product where id = ?";
		List<?> list = new DBHelper().query(sql, id);
		print( response, list.get(0));
	}
	
	
	protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows"); 
		DmProduct dp = new DmProduct();
		BeanUtils.populate(dp, request.getParameterMap());
		List<?> list = pdao.query1(dp, page, rows);
		int total = pdao.count1(dp);
		
		HashMap<String,Object> data = new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print( response, data);
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String,Object>> list = pdao.query();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.getWriter().print(json);
	}
	
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmProduct dp = new DmProduct();
		
		BeanUtils.populate(dp, request.getParameterMap());
		
		if(dp.getPname()==null || dp.getPname().trim().isEmpty()) {
			print( response, new Result(0,"商品名称不能为空!"));
			return;
		}
		if(dp.getShopPrice()==null || dp.getShopPrice()<=0) {
			print( response, new Result(0,"商品商城价格必须大于0!"));
			return;
		}
		// id 为空或者等于0 是新增
		if(dp.getId()== 0) {
			pdao.insert(dp);
		} else {
			pdao.update(dp);
		}
		print( response, new Result(1,"商品保存成功!"));
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		pdao.deleteById(id);
		print(response,new Result(1,"商品删除成功!"));
	}

}
