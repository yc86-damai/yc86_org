package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.PayDao;

@WebServlet("/pay.do")
public class PayServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private PayDao pdao = new PayDao();
   
	protected void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		if(user==null) {
			response.getWriter().append("请先登录系统！");
			return;
		}
		
		String id = ""+user.get("id");
		String payword = request.getParameter("payword");
		String oid = request.getParameter("oid");
		
		Map<String,Object> list =pdao.selectPay(id, payword);
		if(list != null) {
			response.getWriter().print("支付成功");
			pdao.updateByOid(oid);
		}else if(payword==null || payword.length() != 6) {
			response.getWriter().print("请输入六位支付密码");
		}else{
			response.getWriter().print("支付密码错误,请重新输入！");
		}
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> orders = pdao.queryNewOrders(id);
		ret.put("orders", orders);
		ret.put("orderitem", pdao.queryByOid("" + orders.get("id")));
		print(response, ret);
	}
	
	

}
