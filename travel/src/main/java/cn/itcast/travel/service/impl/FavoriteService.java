package cn.itcast.travel.service.impl;

import java.util.List;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteService {
	/**
	 * 获取用户收藏的旅游景点列表
	 * @param uid 用户ID
	 * @return 旅游景点列表
	 */
	public List<Favorite> getFavoriteByUid(String uid);
	
	/**
	 * 根据用户ID和景点ID判断该收藏是否存在
	 * @param uid 用户ID
	 * @param rid 景点ID
	 * @return
	 */
	public boolean findFavoriteByUidAndRid(String uid, String rid);
	
	/**
	 * 根据用户ID和景点ID删除收藏
	 * @param uid 用户ID
	 * @param rid 景点ID
	 * @return 
	 */
	public void deleteFavoriteByUidAndRid(String uid, String rid);
	
	/**
	 * 向tab_favoriate表中插入数据
	 * @param uid 用户ID
	 * @param rid 景点ID
	 */
	public void addFavoriate(String uid, String rid);
	
	/**
	 * 获取ID为rid的旅游景点被客户收藏的次数
	 * @param rid 旅游景点ID
	 * @return 旅游景点被收藏的次数
	 */
	public int getNumberFavoriate(String rid);
}
