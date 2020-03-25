package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
 * Servlet implementation class RegistServlet
 */
@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistServlet() {
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
		// 设置请求头的编码为utf-8
		request.setCharacterEncoding("utf-8");
		
		// 校验前台输入的验证码
		String checkCodeFromPage = request.getParameter("check");
		String checkFromSession = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
		if (!checkCodeFromPage.equalsIgnoreCase(checkFromSession)) {
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码不正确！");
			// 设置响应头的编码为utf-8
			response.setCharacterEncoding("utf-8");
			// 设置响应数据类型
			response.setContentType("application/json;charset=utf-8");
			// 返回json数据
			ObjectMapper objMapper = new ObjectMapper();
			// 将数据写回页面
			objMapper.writeValue(response.getOutputStream(), resultInfo);
			return;
		}
		// 移除session中的验证码
		request.removeAttribute("CHECKCODE_SERVER");
		
		// 获取前台传递过来的数据
		Map<String, String[]> userMap = request.getParameterMap();
		User user = new User();
		UserService userService = new UserServiceImpl();
		try {
			BeanUtils.populate(user, userMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		// 调用保存用户的方法
		boolean isSaveSuccesse = userService.save(user);
		// 判断是否保存成功
		ResultInfo resultInfo = null;
		if(isSaveSuccesse) {
			resultInfo = new ResultInfo(true);
		} else {
			resultInfo = new ResultInfo(false, "注册失败！");
		}
		
		// 设置响应头的编码为utf-8
		response.setCharacterEncoding("utf-8");
		// 设置响应数据类型
		response.setContentType("application/json;charset=utf-8"); 
		// 返回json数据
		ObjectMapper objMapper = new ObjectMapper();
		// 将数据写回页面
		objMapper.writeValue(response.getOutputStream(), resultInfo);
	}

}
