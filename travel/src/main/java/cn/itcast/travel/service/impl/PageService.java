package cn.itcast.travel.service.impl;

import java.util.List;
import java.util.Map;

import cn.itcast.travel.domain.Page;
import cn.itcast.travel.domain.Route;

/**
 * 分页使用的接口
 * @author SHICH
 *
 */
public interface PageService {
	/**
	 * 思路分析
	 * 1、前台传递过来：当前页码、每页显示数据的条目；
	 * 2、后台：总的条目数、当前页码、一共有多少页、每页显示数据的条目、请求的数据列表；
	 * 
	/**
	 * 获取所有数据的条目
	 * @param cid 类别ID
	 * @return 所有数据的数目
	 */
	public int findAllRoute(int cid);
	
	/**
	 * 封装、返回分页对象
	 * @param cid 类别
	 * @param currentPageNum	当前页码
	 * @param pageSize	页面大小
	 * @return	分页对象
	 */
	public Page getDividePageBean(int currentPageNum, int pageSize, int cid);
	
	/**
	 * 根据当前页码和页面大小获取数据
	 * @param currentPageNum	当前页码
	 * @param pageSize	页面大小
	 * @param cid 类别
	 * @return	categoryList<Category>
	 */
	public List<Route> getRouteListByCurrentPageNumAndPageSize(int currentPageNum, int pageSize, int cid);
	
	/**
	 * 通过模糊查询获取数据列表
	 * @param currentPageNum		当前页码
	 * @param pageSize		页面大小
	 * @param cid		种类
	 * @param conditionMapper		模糊查询的条件
	 * @return	旅游景点列表
	 */
	public List<Route> getRouteListByCurrentPageNumAndPageSizeAndQueryCondition(int currentPageNum, int pageSize, int cid, Map<String, String> conditionMapper);
	
	/**
	 * 根据查询条件获取数据的数目
	 * @param cid	种类
	 * @param conditionMapper	模糊查询的条件
	 * @return	旅游景点数目
	 */
	public int findAllRoute(int cid, Map<String, String> conditionMapper);
	
	/**
	 * 获取分页对象
	 * @param currentPageNum	当前页码
	 * @param pageSize		页面大小
	 * @param cid		种类
	 * @param conditionMapper		查询条件
	 * @return		返回分页对象
	 */
	public Page getDividePageBean(int currentPageNum, int pageSize, int cid, Map<String, String> conditionMapper);
}
