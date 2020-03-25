package cn.itcast.travel.dao.impl;

import java.util.List;
import java.util.Map;

import cn.itcast.travel.domain.Route;

public interface RouteDao {
	/**
	 * 获取表tab_route表中数据的数目
	 * @param cid 分类ID
	 * @return int table(tab_route)中数据的数目
	 */
	public int findAllRoute(int cid);
	
	/**
	 * 根据当前页码和页面大小获取用户列表
	 * @param currentPageNum	当前页码
	 * @param pageSize	页面大小
	 * @param cid 分类ID
	 * @return	当前页码和页面大小对应的list
	 */
	public List<Route> getRouteByCurrentPageAndPageSize(int currentPageNum, int pageSize, int cid);
	
	/**
	 * 根据cid和动态的条件查询数据的数目
	 * @param cid 类别
	 * @param conditionMapper 模糊查询条件
	 * @return 数据的数目
	 */
	public int findAllRouteByQueryCondition(int cid, Map<String, String> conditionMapper);
	
	/**
	 * 根据模糊查询条件，获取用户列表
	 * @param currentPageNum
	 * @param pageSize
	 * @param cid
	 * @param conditionMapper
	 * @return
	 */
	public List<Route> getRouteByCurrentPageAndPageSizeAndQueryCondition(int currentPageNum, int pageSize, int cid, Map<String,String> conditionMapper);
	
	/**
	 * 通过商品id获取商品
	 * @param rid 商品id
	 * @return
	 */
	public Route findRouteByRid(String rid);
}
