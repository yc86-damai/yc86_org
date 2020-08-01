package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/BaseAction")
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置请求对象的字符集 ==> post
		request.setCharacterEncoding("UTF-8");
		// 设置响应对象的字符集
		request.setCharacterEncoding("UTF-8");
		// 设置网页的字符集
		response.setContentType("text/html;charset=utf-8");
		
		String op = request.getParameter("op");
		
		try {
			
			Method method = this.getClass().getDeclaredMethod(op, HttpServletRequest.class,HttpServletResponse.class);
			
			method.setAccessible(true);
			
			method.invoke(this,request,response);
		} catch (Exception e) {
			
			e.printStackTrace();
			response.getWriter().append("系统错误");
		} 

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	protected void print(HttpServletResponse response,Object obj) throws  IOException{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		response.getWriter().print(gson.toJson(obj));
	}
	
	
	
}
