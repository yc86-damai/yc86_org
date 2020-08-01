package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.util.DBHelper;


@WebServlet("/clist.do")
public class ClistServlet extends BaseServlet  {
	private static final long serialVersionUID = 1L;
	
	protected void fenlei(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql = "select * from dm_product p INNER JOIN dm_category c on c.id=p.cid where c.id=?";
		List<?> list = new DBHelper().query(sql, id);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
	
	
}
