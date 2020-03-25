package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
	/**
	 * 根据rid查找商家
	 * @param rid 商家ID
	 * @return
	 */
	public Seller findSellerBySid(String sid); 
}
