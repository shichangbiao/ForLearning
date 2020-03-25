package cn.itcast.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.Md5Util;

public class UserDaoImpl implements UserDao {
	
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public User addUser(User user) {
		String sql = "insert into tab_user(uid, username, password, name, birthday, sex, telephone, email,status,code) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println("addUser方法被调用，执行的sql为：" + sql + " 参数为：传入的为User对象");
		jdbcTemplate.update(sql,user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
		return user;
	}
	
	@Override
	public User findUserByName(String userName) {
		// 根据用户名查找用户
		String sql = "select *from tab_user where username = ?";
		User user = null;
		// 手动处理异常，防止查找不到数据
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
		} catch (Exception e) {
			System.out.println("调用findUserByName方法，没有获取到任何数据！userName： " + userName);
		}
		return user;
	}
	
	@Override
	public User findUserByCode(String userCode) {
		String sql = "select *from tab_user where code = ?";
		User user = null;
		// 手动处理异常，防止查找不到数据
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userCode);
		} catch (Exception e) {
			System.out.println("调用findUserByName方法，没有获取到任何数据！userName： " + userCode);
		}
		return user;
	}

	@Override
	public User findUserByUsernameAndPassword(User user) {
		String sql = "select *from tab_user where username = ? and password = ?";
		User retUser = null;
		// 手动处理异常，防止查找不到数据
		try {
			retUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), Md5Util.encodeByMd5(user.getPassword()));
		} catch (Exception e) {
			System.out.println("调用findUserByUsernameAndPassword方法，没有获取到任何数据！userName： " + user.getUsername());
		}
		return retUser;
	}
	
	@Override
	public void updateUserByCode(String userCode) {
		String sql = "update tab_user set status = \'Y\' where code = ?";
		try {
			// 根据验证码更新用户的状态
			jdbcTemplate.update(sql, userCode);
		} catch (Exception e) {
			System.out.println("激活失败！ code为：" + userCode + "的用户不存在！");
		}
	}
}
