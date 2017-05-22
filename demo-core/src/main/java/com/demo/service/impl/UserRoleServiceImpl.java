package com.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.UserRoleDao;
import com.demo.service.UserRoleService;

/**
 * 用户与角色对应关系
 * 
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;

	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if (roleIdList.size() == 0) {
			return;
		}

		// 先删除用户与角色关系
		userRoleDao.delete(userId);

		// 保存用户与角色关系
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		userRoleDao.save(map);
	}

	public List<Long> queryRoleIdList(Long userId) {
		return userRoleDao.queryRoleIdList(userId);
	}

	public void delete(Long userId) {
		userRoleDao.delete(userId);
	}
}
