package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.Route;

public interface RouteService {
	/**
	 * 根据商品id和用户ID获取商品信息
	 * @param rid 商品id
	 * @param sid 用户id
	 * @return 返回商品信息对象
	 */
	public Route getRouteBean(String rid, String sid);
}
