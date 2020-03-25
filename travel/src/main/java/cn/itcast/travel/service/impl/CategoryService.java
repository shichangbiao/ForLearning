package cn.itcast.travel.service.impl;

import java.util.List;

import cn.itcast.travel.domain.Category;

public interface CategoryService {
	// 获取所有的分类信息
	public List<Category> findAllCategory();
}