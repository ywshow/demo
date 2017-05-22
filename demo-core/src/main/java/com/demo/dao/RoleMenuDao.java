package com.demo.dao;

import java.util.List;

import com.demo.entity.RoleMenu;

/**
 * 角色与菜单对应关系
 * 
 */
public interface RoleMenuDao extends BaseDao<RoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
