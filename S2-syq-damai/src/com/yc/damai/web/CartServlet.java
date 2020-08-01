package com.yc.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.CartDao;
import com.yc.damai.util.DBHelper;


@WebServlet("/cart.do")
public class CartServlet extends BaseServlet  {
	private static final long serialVersionUID = 1L;
	
	private CartDao cdao = new CartDao();

	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		if(user==null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}
		
		String uid = ""+user.get("id");
		String pid = request.getParameter("pid");
		
		if( cdao.update(uid, pid) == 0) {
			cdao.insert(uid, pid);
		}
		response.getWriter().append("{\"msg\":\"购物车添加成功!\"}");
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		if(user==null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}
		
		String uid = ""+user.get("id");
		List<?> list = cdao.queryByUid(uid);
		print(response,list);
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql = "delete from dm_cart where id=?";
		int list = new DBHelper().update(sql, id);
		print(response,list);
	}
	
}
