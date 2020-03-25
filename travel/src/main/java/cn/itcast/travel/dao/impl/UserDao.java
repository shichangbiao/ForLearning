package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;

public interface UserDao {
	/**
	 * 新增一个用户
	 * @param user 用户对象
	 * @return 返回一个用户对象 User
	 */
	public User addUser(User user);
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public User findUserByName(String userName);
	
	/**
	 * 通过激活码查询用户
	 * @param userCode 激活码
	 * @return 根据激活码查找到的用户对象 User user
	 */
	public User findUserByCode(String userCode);
	
	/**
	 * 通过用户名和密码获取用户
	 * @param user User对象
	 * @return User对象
	 */
	public User findUserByUsernameAndPassword(User user);
	
	/**
	 * 根据激活码更新用户的激活状态
	 * @param userCode 激活码
	 */
	public void updateUserByCode(String userCode);
	
}
