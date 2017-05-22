package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.entity.Role;


/**
 * 角色
 * 
 */
public interface RoleService {
	
	Role queryObject(Long roleId);
	
	List<Role> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Role role);
	
	void update(Role role);
	
	void deleteBatch(Long[] roleIds);
}
