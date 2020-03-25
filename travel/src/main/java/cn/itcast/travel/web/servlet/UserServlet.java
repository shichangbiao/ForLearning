package cn.itcast.travel.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * User相关处理类
 * @author SHICB
 *
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
	// 用户服务对象
	private UserService userService = new UserServiceImpl();
	
	public void isRegist(HttpServletRequest request, HttpServletResponse response) {
		// 定义响应给调用者的数据
		boolean isLogin = true;
		// 处理参数列表
		User user = (User)request.getSession().getAttribute("currentUser");
		// 处理业务逻辑
		if(null == user) {
			isLogin = false;
		}
		
		if(null != user) {
			isLogin = true;
		}
		// 返回数据
		this.writeMsg(isLogin, response);
	} 
}
