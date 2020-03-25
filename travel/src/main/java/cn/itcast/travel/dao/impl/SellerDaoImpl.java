package cn.itcast.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public Seller findSellerBySid(String sid) {
		// 根据用户名查找用户
		String sql = "select *from tab_seller where sid = ?";
		Seller seller = null;
		// 手动处理异常，防止查找不到数据
		try {
			seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
		} catch (Exception e) {
			System.out.println("调用findSellerBySid方法，没有获取到任何数据！sid： " + sid);
		}
		return seller;
	}
	
	public static void main(String[] args) {
		SellerDaoImpl sellerDaoImpl = new SellerDaoImpl();
		System.out.println(sellerDaoImpl.findSellerBySid("1"));
	}

}
