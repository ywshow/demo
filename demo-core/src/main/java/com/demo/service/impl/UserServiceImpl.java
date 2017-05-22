package com.demo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.UserDao;
import com.demo.entity.User;
import com.demo.service.UserRoleService;
import com.demo.service.UserService;

/**
 * 系统用户
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleService userRoleService;

	public List<String> queryAllPerms(Long userId) {
		return userDao.queryAllPerms(userId);
	}

	public List<Long> queryAllMenuId(Long userId) {
		return userDao.queryAllMenuId(userId);
	}

	public User queryByUserName(String username) {
		return userDao.queryByUserName(username);
	}

	public User queryObject(Long userId) {
		return userDao.queryObject(userId);
	}

	public List<User> queryList(Map<String, Object> map) {
		return userDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return userDao.queryTotal(map);
	}

	@Transactional
	public void save(User user) {
		user.setCreateTime(new Date());
		// sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		userDao.save(user);

		// 保存用户与角色关系
		userRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Transactional
	public void update(User user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		userDao.update(user);

		// 保存用户与角色关系
		userRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Transactional
	public void deleteBatch(Long[] userId) {
		userDao.deleteBatch(userId);
	}

	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return userDao.updatePassword(map);
	}
}
