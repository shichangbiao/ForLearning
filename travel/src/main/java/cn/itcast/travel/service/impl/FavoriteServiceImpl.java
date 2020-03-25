package cn.itcast.travel.service.impl;

import java.util.List;

import cn.itcast.travel.dao.impl.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;

public class FavoriteServiceImpl implements FavoriteService {

	FavoriteDao favoriteDao = new FavoriteDaoImpl();
	@Override
	public List<Favorite> getFavoriteByUid(String uid) {
		return favoriteDao.getFavoriteByUid(uid);
	}

	@Override
	public boolean findFavoriteByUidAndRid(String uid, String rid) {
		return favoriteDao.findFavoriteByUidAndRid(uid, rid);
	}

	@Override
	public void deleteFavoriteByUidAndRid(String uid, String rid) {
		favoriteDao.deleteFavoriteByUidAndRid(uid, rid);
	}

	@Override
	public void addFavoriate(String uid, String rid) {
		favoriteDao.addFavoriate(uid, rid);
	}

	@Override
	public int getNumberFavoriate(String rid) {
		return favoriteDao.getNumberFavoriate(rid);
	}

}
