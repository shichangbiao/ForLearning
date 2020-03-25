package cn.itcast.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;

public class RouteImgDaoImpl implements RouteImgDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<RouteImg> getRouteList(String rid) {
		List<RouteImg> routeImageList = null;
		String sql = "select *from tab_route_img where rid = ?";
		try {
			routeImageList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
		} catch (Exception e) {
			System.out.println("调用方法：getRouteList 出错！");
		}
		return routeImageList;
	}

	/**
	 * 主测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		RouteImgDaoImpl routeImgDaoImpl = new RouteImgDaoImpl();
		System.out.println(routeImgDaoImpl.getRouteList("5"));
	}
}
