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
import com.google.gson.GsonBuilder;
import com.yc.damai.dao.UserDao;
import com.yc.damai.po.DmUser;
import com.yc.damai.util.DBHelper;

@WebServlet("/user.do")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDao udao = new UserDao();
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String ename=request.getParameter("ename");
		String cname=request.getParameter("cname");
		String createtime=request.getParameter("createtime");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String sex=request.getParameter("sex");
		try {
			udao.save(id, ename, cname, createtime, email, phone, sex);
			response.getWriter().print("保存成功");
		}catch(Exception e) {
			e.printStackTrace();
			response.getWriter().print("保存失败");
		}
	}
	
	protected void savepw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String password=request.getParameter("oldpwd");
		String password1 = request.getParameter("newpwd");
		String repass = request.getParameter("repass");
		Map<String,Object> list =udao.querypwd(id, password);
		if(list != null) {
			if(password1==null || password1.trim().isEmpty()) {
				response.getWriter().print("请输入新密码！");
			}else if(password1.equals(repass)==false) {
				response.getWriter().append("两次输入的新密码不一致,请重新输入");
			}else {
				udao.updatepwd(id, password1);
				response.getWriter().append("密码修改成功！");
			}
		}else if(password==null) {
			response.getWriter().print("请输入旧密码！");
		}else{
			response.getWriter().print("旧密码错误,请重新输入！");
		}
	}

	
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmUser du = new DmUser();
		
		BeanUtils.populate(du, request.getParameterMap());
		String repass = request.getParameter("repass");
		String vcode = request.getParameter("vcode");
		String scode = (String)request.getSession().getAttribute("vcode");
		
		if(du.getEname() == null || du.getEname().trim().isEmpty()) {
			response.getWriter().append("请输入用户名");
		}else if(du.getPassword() == null || du.getPassword().trim().isEmpty()) {
			response.getWriter().append("请输入密码");
		}else if (du.getPassword().equals(repass)==false) {
			response.getWriter().append("两次输入的密码不一致");
		}else if(du.getEmail() == null || du.getEmail().trim().isEmpty()) {
			response.getWriter().append("请输入邮箱地址");
		}else if(du.getPhone() == null || du.getPhone().trim().isEmpty()) {
			response.getWriter().append("请输入电话号码");
		}else {
			if(vcode != null && vcode.trim().isEmpty() == false) {
				if(vcode.equalsIgnoreCase(scode)) {
					udao.insert(du);
					response.getWriter().append("用户注册成功!");
				}else {
					response.getWriter().append("验证码错误！");
				}
			}else {
				response.getWriter().append("请输入验证码!");
			}
		}
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql = "select * from dm_user where id=?";
		List<?> list = new DBHelper().query(sql, id);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmUser du = new DmUser();
		
		BeanUtils.populate(du, request.getParameterMap());
		String repass = request.getParameter("repass");
		String vcode = request.getParameter("vcode");
		String scode = (String)request.getSession().getAttribute("vcode");
		
		if(du.getEname() == null || du.getEname().trim().isEmpty()) {
			response.getWriter().append("请输入用户名");
		}else if(du.getEmail() == null || du.getEmail().trim().isEmpty()) {
			response.getWriter().append("请输入邮箱地址");
		}else if(du.getPhone() == null || du.getPhone().trim().isEmpty()) {
			response.getWriter().append("请输入电话号码");
		}else if(du.getPassword() == null || du.getPassword().trim().isEmpty()) {
			response.getWriter().append("请输入新密码");
		}else if (du.getPassword().equals(repass)==false) {
			response.getWriter().append("两次输入的新密码不一致");
		}else {
			if(vcode != null && vcode.trim().isEmpty() == false) {
				if(vcode.equalsIgnoreCase(scode)) {
					udao.update(du);
					response.getWriter().append("密码重置成功!");
				}else {
					response.getWriter().append("验证码错误！");
				}
			}else {
				response.getWriter().append("请输入验证码!");
			}
		}
	}
	
	protected void queryUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(udao.queryUserById(id));
		response.getWriter().print(json);
	}

}
