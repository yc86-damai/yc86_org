package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yc.damai.dao.AddrDao;
import com.yc.damai.po.DmAddress;
import com.yc.damai.po.Result;

@WebServlet("/addr.do")
public class AddrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private AddrDao adao = new AddrDao();

	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}

		String uid = "" + user.get("id");

		List<Map<String,Object>> list = adao.query(uid);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(list);
		response.getWriter().print(json);

	}

	protected void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmAddress da = new DmAddress();

		BeanUtils.populate(da, request.getParameterMap());
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		if(user==null) {
			response.getWriter().append("{\"msg\":\"请先登录系统！\"}");
			return;
		}
		
		String uid = ""+user.get("id");

		if (da.getAddr() == null || da.getAddr().trim().isEmpty()) {
			print(response, new Result(0, "地址不能为空!"));
			return;
		}
		if (da.getPhone() == null || da.getPhone().trim().isEmpty()) {
			print(response, new Result(0, "电话号码不能为空!"));
			return;
		}
		// id 为空或者等于0 是新增
		if (da.getId() == 0) {
			adao.insert(uid,da);
		} else {
			adao.update(uid,da);
		}
		print(response, new Result(1, "地址保存成功!"));
	}
	
	protected void updateByUid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		String uid = "" + user.get("id");
		String id = request.getParameter("id");

		try {
			adao.updateByUid(uid);
			adao.updateById(id);
			print(response, new Result(1, "保存成功!"));
		}catch(Exception e) {
			e.printStackTrace();
			print(response, new Result(0, "保存失败!"));
		}
	}
	
	protected void updateByUid1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");

		String uid = "" + user.get("id");
		String id = request.getParameter("id");

		try {
			adao.updateByAddrUid(uid);
			adao.updateByAddrId(id);
			print(response, new Result(1, "保存成功!"));
		}catch(Exception e) {
			e.printStackTrace();
			print(response, new Result(0, "保存失败!"));
		}
	}
}
