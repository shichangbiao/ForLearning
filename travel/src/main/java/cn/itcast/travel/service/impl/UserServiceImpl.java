package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.Md5Util;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = new UserDaoImpl();

	@Override
	public User addUser(User user) {
		try {
			// 对传递过来的密码进行加密
			user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDao.addUser(user);
	}

	@Override
	public boolean save(User user) {
		User retUser = userDao.findUserByName(user.getName());
		// 判断是否获取到retUser
		if(null == retUser) {
			try {
				// 生成激活码
				String activeCode = UuidUtil.getUuid();
				user.setCode(activeCode);
				user.setStatus("N");
				user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
				userDao.addUser(user);
				// 发送激活邮件
				String activeUrl = "<a href='http://127.0.0.1:8080/travel/activeServlet?activeCode="+ activeCode +"'>点击激活【测试网站】</a>";
				// 发送激活邮件
				try {
					MailUtils.sendMail(user.getEmail(), activeUrl, "测试网站激活邮件");
				} catch (Exception e) {
					System.out.println("激活邮件发送失败！ 邮箱地址为： " + user.getEmail());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean activeUser(String userCode) {
		User user = null;
		// 根据激活码查找用户
		user = userDao.findUserByCode(userCode);
		// 更新用户的状态
		if(null != user) {
			userDao.updateUserByCode(userCode);
			return true;
		}
		// 激活失败，返回fasle
		return false;
	}

	@Override
	public boolean login(User user) {
		boolean isPermit = false;
		User retUser = userDao.findUserByUsernameAndPassword(user);
		if(null != retUser) {
			isPermit = true;
		}
		return isPermit;
	}

	@Override
	public User findUserByUserName(String username) {
		return userDao.findUserByName(username);
	}
	
}
