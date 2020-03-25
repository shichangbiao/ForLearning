package cn.itcast.travel.web.servlet;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.domain.Page;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.impl.PageService;
import cn.itcast.travel.service.impl.PageServiceImpl;

@WebServlet("/pageServlet/*")
public class PageServlet extends BaseServlet {
	PageService pageService = new PageServiceImpl();
	/**
	 * 处理分页请求
	 * @param request	请求
	 * @param response	响应
	 */
	public void getDividePageBean(HttpServletRequest request, HttpServletResponse response) {
		int currentPage = 0;
		int pageSize = 0;
		int cid = 0;
		// 处理请求数据
		try {
			currentPage = Integer.valueOf(request.getParameter("currentPage"));
			pageSize = Integer.valueOf(request.getParameter("pageSize"));
			cid = Integer.valueOf(request.getParameter("cid"));
		} catch (Exception e) {
			currentPage = 0;
			pageSize = 5;
			cid = 5;
		}
		// 处理分页请求
		Page retPage = pageService.getDividePageBean(currentPage, pageSize, cid);
		// 响应分页请求
		this.writeMsg(retPage, response);
	}
	
	/**
	 * 根据条件对tab_route进行模糊查询
	 * @param request	请求
	 * @param response	响应
	 */
	public void getDividePageBeanByqc(HttpServletRequest request, HttpServletResponse response) {
		// 获取请求传递过来的参数
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		String queryCondition = request.getParameter("rname");
		
		// 处理请求传递过来的参数
		int integerCid = (null==cid)?0:Integer.valueOf(cid);
		int integerCurrentPage = (null==currentPage)?1:Integer.valueOf(currentPage);
		int integerPageSize = (null==pageSize)?5:Integer.valueOf(pageSize);
		Map<String, String> conditionMapper = new HashMap<String, String>();
		// 如果模糊查询的条件不为空
		if(null != queryCondition) {
			queryCondition = URLDecoder.decode(queryCondition);
			conditionMapper.put("rname", queryCondition);
		}
		// 如果模糊查询的条件为空
		if(null == queryCondition) {
			conditionMapper = new HashMap<String, String>(); 
		}
		// 处理请求数据
		Page retPage = new Page();
		retPage = pageService.getDividePageBean(integerCurrentPage, integerPageSize, integerCid, conditionMapper);
		// 返回数据
		this.writeMsg(retPage, response);
	}
}
