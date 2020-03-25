package cn.itcast.travel.dao.impl;

import java.util.List;

import cn.itcast.travel.domain.Category;

public interface CategoryDao {
	/**
	 * 获取页面所有的分类
	 * @return List 所有分类的列表
	 */
	public List<Category> findAll();
	
	/**
	 * 获取表tab_category表中数据的数目
	 * @return int table(tab_category)中数据的数目
	 */
	public int findAllCategory();
	
	/**
	 * 根据当前页码和页面大小获取用户列表
	 * @param currentPageNum	当前页码
	 * @param pageSize	页面大小
	 * @return	当前页码和页面大小对应的list
	 */
	public List<Category> getCategoryByCurrentPageAndPageSize(int currentPageNum, int pageSize);
}
