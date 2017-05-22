package com.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.RoleDao;
import com.demo.entity.Role;
import com.demo.service.RoleMenuService;
import com.demo.service.RoleService;

/**
 * 角色
 * 
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuService roleMenuService;

	public Role queryObject(Long roleId) {
		return roleDao.queryObject(roleId);
	}

	public List<Role> queryList(Map<String, Object> map) {
		return roleDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return roleDao.queryTotal(map);
	}

	@Transactional
	public void save(Role role) {
		role.setCreateTime(new Date());
		roleDao.save(role);
		// 保存角色与菜单关系
		roleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Transactional
	public void update(Role role) {
		roleDao.update(role);
		// 更新角色与菜单关系
		roleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Transactional
	public void deleteBatch(Long[] roleIds) {
		roleDao.deleteBatch(roleIds);
	}

}
