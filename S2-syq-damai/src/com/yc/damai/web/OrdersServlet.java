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


import com.yc.damai.dao.CartDao;
import com.yc.damai.dao.OrderitemDao;
import com.yc.damai.dao.OrdersDao;
import com.yc.damai.po.Result;

@WebServlet("/orders.do")
public class OrdersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private OrdersDao odao = new OrdersDao();
	private OrderitemDao oidao = new OrderitemDao();
	private CartDao cdao = new CartDao();

	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}

		String uid = "" + user.get("id");

		odao.insert(uid);
		oidao.insert(uid);
		cdao.deleteByUid(uid);
		response.getWriter().append("{\"code\":\"1\"}");
	}

	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}

		String uid = "" + user.get("id");
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> orders = odao.queryNewOrders(uid);
		ret.put("orders", orders);
		ret.put("orderitem", oidao.queryByOid("" + orders.get("id")));
		print(response, ret);
	}

	protected void queryOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String ename = request.getParameter("ename");
		String name = request.getParameter("name");
		List<Map<String, Object>> list = odao.query(ename, name, page, rows);
		int total = odao.count(ename, name);
		HashMap<String, Object> data = new HashMap<>();

		data.put("rows", list);
		data.put("total", total);
		
		print( response, data);
	}
	
	protected void queryOrder1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String ename = request.getParameter("ename");
		String name = request.getParameter("name");
		List<Map<String, Object>> list = odao.query1(ename, name, page, rows);
		int total = odao.count1(ename, name);
		
		HashMap<String, Object> data = new HashMap<>();

		data.put("rows", list);
		data.put("total", total);
		
		print( response, data);
	}
	
	protected void queryOrder2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String ename = request.getParameter("ename");
		String name = request.getParameter("name");
		List<Map<String, Object>> list = odao.query2(ename, name, page, rows);
		int total = odao.count2(ename, name);
		HashMap<String, Object> data = new HashMap<>();

		data.put("rows", list);
		data.put("total", total);
		
		print( response, data);
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		odao.update(id);
		print( response, new Result(1,"商品已发出!"));
	}

}
