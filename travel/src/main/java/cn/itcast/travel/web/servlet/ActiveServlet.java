package cn.itcast.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * Servlet implementation class ActiveServlet
 */
@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActiveServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取激活码
		String activeCode = request.getParameter("activeCode");
		// 调用激活用户的方法
		UserService userService = new UserServiceImpl();
		boolean isActive = userService.activeUser(activeCode);
		// 判断是否激活，如果已经激活，就直接提示用户跳转到登录页面
		if(isActive) {
			String loginTipsUrl = "激活成功！请<a href='login.html'>登录</a>";
			response.setCharacterEncoding("utf-8");
//			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(loginTipsUrl);
		} else {
			String loginTipsUrl = "激活失败，请联系管理员【QQ：1985564478】！";
			response.setCharacterEncoding("utf-8");
//			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(loginTipsUrl);
		}
	}
}
