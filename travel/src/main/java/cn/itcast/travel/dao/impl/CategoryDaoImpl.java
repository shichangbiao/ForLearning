package cn.itcast.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {
	
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public List<Category> findAll() {
		String sql = "select * from tab_category ";
		List<Category> categoryList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
        return categoryList;
	}

	@Override
	public int findAllCategory() {
		String sql = "select count(*) from tab_category";
		int categoryNums = 0;
		try {
			categoryNums = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			System.out.println("没有查询到任何数据，findAllCategory");
			return 0;
		}
		return categoryNums;
	}

	@Override
	public List<Category> getCategoryByCurrentPageAndPageSize(int currentPageNum, int pageSize) {
		List<Category> categoryList = null;
		String sql = "select *from tab_category limit ?,?";
		try {
			categoryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class),(currentPageNum-1)*currentPageNum,pageSize);
		} catch (Exception e) {
			System.out.println("调用方法：getCategoryByCurrentPageAndPageSize 出错！");
		}
		return categoryList;
	}
	
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
		System.out.println(categoryDaoImpl.getCategoryByCurrentPageAndPageSize(0, 5));
		System.out.println(categoryDaoImpl.findAllCategory());
	}
}
