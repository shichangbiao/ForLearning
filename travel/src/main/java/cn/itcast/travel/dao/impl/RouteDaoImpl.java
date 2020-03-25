package cn.itcast.travel.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;

public class RouteDaoImpl implements RouteDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public int findAllRoute(int cid) {
		String sql = "select count(*) from tab_route where cid = '" + cid + "'";
		int routeNums = 0;
		try {
			routeNums = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			System.out.println("没有查询到任何数据，findAllCategory");
			return 0;
		}
		return routeNums;
	}

	@Override
	public List<Route> getRouteByCurrentPageAndPageSize(int currentPageNum, int pageSize, int cid) {
		List<Route> routeList = null;
		String sql = "select *from tab_route where cid = ? limit ?,?";
		try {
			routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid,
					(currentPageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			System.out.println("调用方法：getCategoryByCurrentPageAndPageSize 出错！");
		}
		return routeList;
	}

	@Override
	public int findAllRouteByQueryCondition(int cid, Map<String, String> conditionMapper) {
		// 定义返回参数
		int routeNumber = 0;
		// 对传递过来的参数进行处理
		if (null == conditionMapper) {
			conditionMapper = new HashMap<String, String>();
		}
		// 动态的拼接SQL
		String sql = "select count(*) from tab_route where cid = '" + cid + "' ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		for (String key : conditionMapper.keySet()) {
			String sqlPiece = "and " + key + " like '%" + conditionMapper.get(key) + "%'";
			sb.append(sqlPiece);
		}
		sb.append("and 1=1");
		System.out.println(sb.toString());
		// 根据拼接的Sql查询数据的数目
		try {
			routeNumber = jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		} catch (Exception e) {
			System.out.println(sb.toString());
			System.out.println("没有查询到任何数据，findAllRouteByQueryCondition");
			return 0;
		}
		return routeNumber;
	}

	@Override
	public List<Route> getRouteByCurrentPageAndPageSizeAndQueryCondition(int currentPageNum, int pageSize, int cid,
			Map<String, String> conditionMapper) {
		// 定义返回参数
		List<Route> routeList = new ArrayList<Route>();
		// 处理参数
		if (currentPageNum <= 0) {
			currentPageNum = 1;
		}
		// 默认分页页面的大小为5
		if (0 >= pageSize) {
			pageSize = 5;
		}
		// 避免传递过来的condition为空，出现空指针异常
		if (null == conditionMapper) {
			System.out.println("调用getRouteByCurrentPageAndPageSizeAndQueryCondition方法时，conditionMapper为空");
			conditionMapper = new HashMap<String, String>();
		}
		// 动态拼接SQL
		StringBuilder sb = new StringBuilder();
		String sql = "select *from tab_route where cid = ? ";
		sb.append(sql);
		for (String key : conditionMapper.keySet()) {
			String sqlPiece = "and " + key + " like " + "'%" + conditionMapper.get(key) + "%'";
			sb.append(sqlPiece);
		}
		String limit = " limit ? ,?";
		sb.append(limit);
		System.out.println("生成的sql为：" + sb.toString());
		// 执行查询，处理异常
		try {
			routeList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), cid,
					(currentPageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			System.out.println(sb.toString());
			routeList = new ArrayList<Route>();
			System.out.println("调用方法：getRouteByCurrentPageAndPageSizeAndQueryCondition 出错！");
		}
		// 处理返回数据
		return routeList;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RouteDaoImpl routeDaoImpl = new RouteDaoImpl();
		Map<String, String> conditionMapper = new HashMap<String, String>();
		conditionMapper.put("rflag", "1");
		System.out.println(routeDaoImpl.findAllRouteByQueryCondition(5, conditionMapper));
		System.out.println(routeDaoImpl.getRouteByCurrentPageAndPageSizeAndQueryCondition(1, 5, 5, conditionMapper));
	}

	@Override
	public Route findRouteByRid(String rid) {
		// 根据用户名查找用户
		String sql = "select *from tab_route where rid = ?";
		Route route = null;
		// 手动处理异常，防止查找不到数据
		try {
			route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
		} catch (Exception e) {
			System.out.println("调用findRouteByRid方法，没有获取到任何数据！rid： " + rid);
		}
		return route;
	}

}
