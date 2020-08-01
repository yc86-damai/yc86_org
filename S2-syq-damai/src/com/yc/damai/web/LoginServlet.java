package com.yc.damai.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.UserDao;

@WebServlet("/login.do")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserDao udao = new UserDao();

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ename = request.getParameter("ename");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		String scode = (String) request.getSession().getAttribute("vcode");

		Map<String, Object> user = udao.selectByLogin(ename, password);
		if (user != null) {
			request.getSession().setAttribute("loginedUser", user);
			if (vcode != null && vcode.trim().isEmpty() == false) {
				if (vcode.equalsIgnoreCase(scode)) {
					response.getWriter().print("登录成功");
				} else {
					response.getWriter().append("验证码错误！");
				}
			} else {
				response.getWriter().append("请输入验证码!");
			}
		}else if (ename == null || ename.trim().isEmpty()) {
			response.getWriter().append("请输入用户名!");
		} else if (password == null || password.trim().isEmpty()) {
			response.getWriter().append("请输入密码!");
		} else {
			response.getWriter().print("密码或用户名错误！");
		}
	}

	protected void login1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		String scode = (String) request.getSession().getAttribute("vcode");

		Map<String, Object> user = udao.selectAddrByLogin(username, password);
		if (user != null) {
			if (vcode != null && vcode.trim().isEmpty() == false) {
				if (vcode.equalsIgnoreCase(scode)) {
					response.getWriter().print("登录成功");
				} else {
					response.getWriter().append("验证码错误！");
				}
			} else {
				response.getWriter().append("请输入验证码!");
			}
		} else if (username == null || username.trim().isEmpty()) {
			response.getWriter().append("请输入用户名!");
		} else if (password == null || password.trim().isEmpty()) {
			response.getWriter().append("请输入密码!");
		} else {
			response.getWriter().print("密码或用户名错误！");
		}

	}

}
