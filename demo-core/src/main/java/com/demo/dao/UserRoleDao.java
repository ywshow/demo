package com.demo.dao;

import java.util.List;

import com.demo.entity.UserRole;

/**
 * 用户与角色对应关系
 * 
 */
public interface UserRoleDao extends BaseDao<UserRole> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
