package cn.itcast.travel.dao.impl;

import java.util.List;

import cn.itcast.travel.domain.RouteImg;

public interface RouteImgDao {
	/**
	 * 根据商品ID获取对应图片列表
	 * @param rid
	 * @return
	 */
	public List<RouteImg> getRouteList(String rid);
}
