package cn.itcast.travel.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;

public class FavoriteDaoImpl implements FavoriteDao {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	
	@Override
	public List<Favorite> getFavoriteByUid(String uid) {
		List<Favorite> favoriteList = new ArrayList<Favorite>();
		String sql = "select *from tab_favorite where uid = ?";
		try {
			favoriteList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid);
		} catch (Exception e) {
			System.out.println("调用方法：getFavoriteByUid 出错！");
		}
		return favoriteList;
	}

	@Override
	public boolean findFavoriteByUidAndRid(String uid, String rid) {
		Favorite favorite = null;
		boolean isFinded = true;
		String sql = "select *from tab_favorite where uid = ? and rid = ?";
		// 手动处理异常，防止查找不到数据
		try {
			favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
		} catch (Exception e) {
			isFinded = false;
			System.out.println("调用findFavoriteByUidAndRid方法，没有获取到任何数据！uid： " + uid);
		}
		
		if(null == favorite) {
			isFinded = false;
		}
		return isFinded;
	}
	
	@Override
	public void deleteFavoriteByUidAndRid(String uid, String rid) {
		// 根据uid和rid删除收藏
		String sql = "delete from tab_favorite where uid = ? and rid = ?";
		System.out.println("deleteFavoriteByUidAndRid方法被调用，执行的sql为：" + sql + " 参数为：传入的为uid和rid");
		jdbcTemplate.update(sql, uid, rid);
	}

	@Override
	public void addFavoriate(String uid, String rid) {
		String sql = "insert tab_favorite(rid, date, uid) values(?,?,?)";
		System.out.println("addFavoriate方法被调用，执行的sql为：" + sql + " 参数为：传入的为uid、rid");
		jdbcTemplate.update(sql, rid, new Date(),uid);
	}

	@Override
	public int getNumberFavoriate(String rid) {
		int number = 0;
		String sql = "select count(*) from tab_favorite where rid = '" + rid + "'";
		try {
			number = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			number = 0;
		}
		return number;
	}
	
}
