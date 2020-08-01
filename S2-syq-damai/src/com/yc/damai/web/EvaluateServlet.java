package com.yc.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yc.damai.dao.EvaluateDao;

@WebServlet("/evaluate.do")
public class EvaluateServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private EvaluateDao edao = new EvaluateDao();

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}

		String uid = "" + user.get("id");
		String content = request.getParameter("content");
		String image = request.getParameter("image");
		String pid = request.getParameter("pid");
		
		if(content == null ||content.trim().isEmpty()) {
			response.getWriter().append("请输入您的评论");
		}else {
			edao.insert(content, image, pid, uid);
			response.getWriter().append("评论发表成功!");
		}
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");

		List<Map<String, Object>> list = edao.query(pid);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(list);
		response.getWriter().print(json);
	}

	
}
