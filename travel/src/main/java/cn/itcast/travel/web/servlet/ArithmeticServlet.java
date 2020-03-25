package cn.itcast.travel.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.service.impl.ArithemeticService;
import cn.itcast.travel.service.impl.ArithemeticServiceImpl;

/**
 * Servlet implementation class ArithmeticServlet
 */
@WebServlet("/arithmetic/*")
public class ArithmeticServlet extends BaseServlet {
	
	// 数学处理类
	ArithemeticService arithemeticService = new ArithemeticServiceImpl();
	
	/**
	 * 生成小学四则运算题目
	 * @param request http请求
	 * @param response http响应
	 */
	public void generatItems(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("generatItems method invoked!");
	}
}
