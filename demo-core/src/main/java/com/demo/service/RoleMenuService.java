package com.demo.service;

import java.util.List;



/**
 * 角色与菜单对应关系
 * 
 */
public interface RoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
	
}
