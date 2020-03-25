package cn.itcast.travel.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.impl.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;


@WebServlet("/routeServlet/*")
public class RouteServlet extends BaseServlet {
	RouteService routeService = new RouteServiceImpl();
	public void getRouteBean(HttpServletRequest request, HttpServletResponse response) {
		// 获取请求转发过来的参数
		String sid = request.getParameter("sid");
		String rid = request.getParameter("rid");
		// 定义使用的参数
		Route route = new Route();
		// 处理请求转发过来的参数
		// 处理业务逻辑
		route = routeService.getRouteBean(rid, sid);
		// 组织返回给前台的对象
		this.writeMsg(route, response);
		// 返回数据给前台
	}
}
