package cn.itcast.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.itcast.travel.dao.impl.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao categoryDao = new CategoryDaoImpl();
	Jedis jedis = new Jedis();
	@Override
	public List<Category> findAllCategory() {
		// 查询redis缓存检查是否有数据
//		Set<String> categoriesRedis = jedis.zrange("REDIS_CATEGORY", 0, -1);
		Set<Tuple> categoriesRedis = jedis.zrangeByScoreWithScores("REDIS_CATEGORY", 0, -1);
		// 从数据库中获取的数据
		List<Category> categoryList = new ArrayList<Category>();
		// 如果没有则查询数据库
		if(null == categoriesRedis || 0 == categoriesRedis.size()) {
			System.out.println("query database！！！");
			categoryList = categoryDao.findAll();
			// 将从数据库中查询出的数据存入redis
			for(int i = 0; i < categoryList.size(); i++) {
				jedis.zadd("REDIS_CATEGORY", categoryList.get(i).getCid(), categoryList.get(i).getCname());
			}
		}
		// 数据类型转换，因为数据库查询返回的是List，Redis查询返回的是Set
		Category category = null;
		for(Tuple tuple:categoriesRedis) {
			category = new Category();
			category.setCid((int)tuple.getScore());
			category.setCname(tuple.getElement());
			categoryList.add(category);
		}
		return categoryList;
	}

}
