package com.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.RoleMenuDao;
import com.demo.service.RoleMenuService;

/**
 * 角色与菜单对应关系
 * 
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if (menuIdList.size() == 0) {
			return;
		}
		// 先删除角色与菜单关系
		roleMenuDao.delete(roleId);

		// 保存角色与菜单关系
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		roleMenuDao.save(map);
	}

	public List<Long> queryMenuIdList(Long roleId) {
		return roleMenuDao.queryMenuIdList(roleId);
	}

}
