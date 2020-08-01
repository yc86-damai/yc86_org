package com.yc.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yc.damai.dao.OlistDao;

@WebServlet("/olist.do")
public class OlistServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private OlistDao odao = new OlistDao();

	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}

		String uid = "" + user.get("id");

		List<Map<String, Object>> list = odao.query(uid);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.getWriter().print(json);
	}

	protected void queryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		List<Map<String, Object>> list = odao.queryById(id);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		// response.getWriter().append("{\"msg\":\"收货成功！\"}");
		// response.getWriter().print(json);
		String a = json.substring(11, 14);
		//response.getWriter().print(a);

		if (a.equalsIgnoreCase("未发货")) {
			response.getWriter().append("{\"msg\":\"您的商品还未发货！\"}");
		} else if (a.equalsIgnoreCase("未支付")) {
			response.getWriter().append("{\"msg\":\"您的商品还未付款！\"}");
		} else if (a.equalsIgnoreCase("已收货")) {
			response.getWriter().append("{\"msg\":\"您已收货！\"}");
		} else {
			response.getWriter().append("{\"msg\":\"收货成功！\"}");
			odao.updateById(id);
		}
	}
	
	protected void queryById1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		List<Map<String, Object>> list = odao.queryById(id);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		// response.getWriter().append("{\"msg\":\"收货成功！\"}");
		// response.getWriter().print(json);
		String a = json.substring(11, 14);
		//response.getWriter().print(a);

		if (a.equalsIgnoreCase("已收货")) {
			response.getWriter().append("{\"msg\":\"请分享您的使用心得!\"}");
		} else {
			response.getWriter().append("{\"msg\":\"您的订单还未完成！\"}");
		}

	}
	
	
	
	
}
