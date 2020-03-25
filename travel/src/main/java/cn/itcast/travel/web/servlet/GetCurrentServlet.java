package cn.itcast.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * Servlet implementation class GetCurrentServlet
 */
@WebServlet("/getCurrentServlet")
public class GetCurrentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCurrentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取当前登录用户对应的用户信息
		User currentLoginUser = (User)request.getSession().getAttribute("currentUser");
		// 定义消息对象
		ResultInfo resultInfo = new ResultInfo();
		UserService userService = new UserServiceImpl();
		if(null != currentLoginUser) {
			currentLoginUser = userService.findUserByUserName(currentLoginUser.getUsername());
		}
		// 判断session中是否存在当前登录的用户对象
		if(null != currentLoginUser) {
			resultInfo.setData(currentLoginUser);
			resultInfo.setFlag(true);
			resultInfo.setErrorMsg("欢迎您！" + currentLoginUser.getName());
		}
		
		if(null == currentLoginUser) {
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("请登录！");
		}
		
		// 返回消息给调用者
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(), resultInfo);
	}

}
