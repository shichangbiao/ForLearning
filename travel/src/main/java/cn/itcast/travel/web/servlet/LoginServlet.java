package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		// 设置请求的编码为utf-8
		request.setCharacterEncoding("utf-8");
		// 封装返回的信息对象
		ResultInfo resultInfo = new ResultInfo();
		// 用户业务对象
		UserService userService = new UserServiceImpl();
		// 获取页面传递过来的消息
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			// 将当前登录用户的对象放入session中
			System.out.println("Session: " + user.getUsername());
			request.getSession().setAttribute("currentUser", user);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// 校验验证码
		String checkCodeFromPage = request.getParameter("check");
		String checkCodeFromSession = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
		System.out.println(checkCodeFromPage + " " + checkCodeFromSession);
		if(null != checkCodeFromPage && null != checkCodeFromSession && checkCodeFromPage.equalsIgnoreCase(checkCodeFromSession)) {
			// 移除session中的CHECKCODE_SERVER
			request.getSession().removeAttribute("CHECKCODE_SERVER");
			// 校验用户名和密码
			if(userService.login(user)) {
				resultInfo.setFlag(true);
				resultInfo.setErrorMsg("登录成功！");
				resultInfo.setData(user);
				
			}
			if(!userService.login(user)) {
				resultInfo.setFlag(false);
				resultInfo.setErrorMsg("用户名或者密码错误！");
			}
		} else {
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码输入错误！");
		}
		// 将消息返回给前台页面
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(), resultInfo);
		
	}

}
