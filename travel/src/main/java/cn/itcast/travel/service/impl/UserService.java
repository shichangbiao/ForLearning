package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.User;

public interface UserService {
	/**
	 * 增加一个用户
	 * @param user 从前台传递过来的用户对象
	 * @return 返回一个 用户对象 User
	 */
	@Deprecated
	User addUser(User user);
	
	/**
	 * 保存用户
	 * @param user User对象
	 * @return 保存成功的标志
	 */
	boolean save(User user);
	
	/**
	 * 激活用户
	 * @param userCode 激活码
	 * @return
	 */
	boolean activeUser(String userCode);
	
	/**
	 * 用户登录
	 * @param user User对象
	 * @return boolean 是否允许登录
	 */
	boolean login(User user);
	
	/**
	 * 根据用户名获取用户
	 * @param username 用户账号
	 * @return User对象
	 */
	User findUserByUserName(String username);
}
