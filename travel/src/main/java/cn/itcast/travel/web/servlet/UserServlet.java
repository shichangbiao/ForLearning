package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * User相关处理类
 * 
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
		User user = (User) request.getSession().getAttribute("currentUser");
		// 处理业务逻辑
		if (null == user) {
			isLogin = false;
		}

		if (null != user) {
			isLogin = true;
		}
		// 返回数据
		this.writeMsg(isLogin, response);
	}

	/**
	 * 通过邮箱激活用户
	 * 
	 * @param request  请求
	 * @param response 响应
	 * @throws IOException
	 */
	public void avtiveUserByEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取激活码
		String activeCode = request.getParameter("activeCode");
		// 调用激活用户的方法
		UserService userService = new UserServiceImpl();
		boolean isActive = userService.activeUser(activeCode);
		// 判断是否激活，如果已经激活，就直接提示用户跳转到登录页面
		if (isActive) {
			String loginTipsUrl = "激活成功！请<a href='login.html'>登录</a>";
			this.writeMsg(loginTipsUrl, response);
		} else {
			String loginTipsUrl = "激活失败，请联系管理员【QQ：1985564478】！";
			this.writeMsg(loginTipsUrl, response);
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param request
	 * @param response
	 */
	public void registUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("utf-8");
		// 页面验证码
		String checkCodeFromPage = request.getParameter("check");
		// 后台验证码
		String checkFromSession = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
		if (!checkCodeFromPage.equalsIgnoreCase(checkFromSession)) {
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码不正确！");
			// 将数据写回页面
			this.writeMsg(resultInfo, response);
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
		if (isSaveSuccesse) {
			resultInfo = new ResultInfo(true);
		} else {
			resultInfo = new ResultInfo(false, "注册失败！");
		}
		// 响应请求
		this.writeMsg(resultInfo, response);
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 使当前的session失效
		request.getSession().invalidate();
		// 跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 设置请求的编码为utf-8
		request.setCharacterEncoding("utf-8");
		// 封装返回的信息对象
		ResultInfo resultInfo = new ResultInfo();
		// 获取页面传递过来的消息
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			// 将当前用户的完整信息存入到session中
			User SESSION_CURRENTUSER = new User();
			SESSION_CURRENTUSER = userService.findUserByUserName(user.getUsername());
			request.getSession().setAttribute("currentUser", SESSION_CURRENTUSER);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// 校验验证码
		String checkCodeFromPage = request.getParameter("check");
		String checkCodeFromSession = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
		if (null != checkCodeFromPage && null != checkCodeFromSession
				&& checkCodeFromPage.equalsIgnoreCase(checkCodeFromSession)) {
			// 移除session中的CHECKCODE_SERVER
			request.getSession().removeAttribute("CHECKCODE_SERVER");
			// 校验用户名和密码
			if (userService.login(user)) {
				resultInfo.setFlag(true);
				resultInfo.setErrorMsg("登录成功！");
				resultInfo.setData(user);
			}
			if (!userService.login(user)) {
				resultInfo.setFlag(false);
				resultInfo.setErrorMsg("用户名或者密码错误！");
			}
		} else {
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码输入错误！");
		}
		// 将消息返回给前台页面
		this.writeMsg(resultInfo, response);
	}
	
	/**
	 * 获取当前用户
	 * @param request
	 * @param response
	 */
	public void isLogin(HttpServletRequest request, HttpServletResponse response) {
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
				this.writeMsg(resultInfo, response);
	}
}
