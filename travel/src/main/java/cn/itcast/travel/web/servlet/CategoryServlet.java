package cn.itcast.travel.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.impl.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;


@WebServlet("/categoryServlet/*")
public class CategoryServlet extends BaseServlet {
	// 分类服务类
	CategoryService categoryService = new CategoryServiceImpl();
	
	/**
	 * 获取所有的分类
	 * @param request 请求
	 * @param response 响应
	 */
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) {
		List<Category> categoryList = categoryService.findAllCategory();
		System.out.println("here!");
		this.writeMsg(categoryList, response);
	}
}
