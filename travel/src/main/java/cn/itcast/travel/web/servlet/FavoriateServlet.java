package cn.itcast.travel.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

@WebServlet("/favoriateServlet/*")
public class FavoriateServlet extends BaseServlet {
	private FavoriteService favoriateService = new FavoriteServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	public void addFavoriate(HttpServletRequest request, HttpServletResponse response) {
		boolean isAddedSuccess = true;
		// 处理请求数据
		User currentUser = (User)request.getSession().getAttribute("currentUser");
		String rid = request.getParameter("rid");
		// 处理业务逻辑
		// 当前没有用户登录
		if(null == currentUser) {
			isAddedSuccess = false;
			response.setContentType("application/json;charset=utf-8");
			this.writeMsg(isAddedSuccess, response);
			return;
		}
		// 用户登录
		if(null != currentUser) {
			System.out.println("用户为：" + currentUser.getUid());
			currentUser = userService.findUserByUserName(currentUser.getUsername());
			favoriateService.addFavoriate(String.valueOf(currentUser.getUid()), rid);
			response.setContentType("application/json;charset=utf-8");
			this.writeMsg(isAddedSuccess, response);
		}
	}
	/**
	 * 根据景点ID判断用户是否已经收藏该景点
	 * @param request
	 * @param response
	 */
	public void findFavoriteByUidAndRid(HttpServletRequest request, HttpServletResponse response) {
		// 定义返回的参数列表
		boolean isCollected = false;
		// 处理请求传递过来的参数
		String rid = request.getParameter("rid");
		User currentUser = (User)request.getSession().getAttribute("currentUser");
		currentUser = userService.findUserByUserName(currentUser.getUsername());
		// 处理业务逻辑
		if(null != currentUser) {
			isCollected = favoriateService.findFavoriteByUidAndRid(String.valueOf(currentUser.getUid()), rid);
		}
		// 响应请求
		this.writeMsg(isCollected, response);
	}
	/**
	 * 根据景点ID获取，对应景点已经被收藏的次数
	 * @param request
	 * @param response
	 */
	public void getNumberFavoriate(HttpServletRequest request, HttpServletResponse response) {
		// 处理请求中传递过来的参数
		String rid = request.getParameter("rid");
		if(null != rid) {
			this.writeMsg(favoriateService.getNumberFavoriate(rid), response);
		}
	}
}
