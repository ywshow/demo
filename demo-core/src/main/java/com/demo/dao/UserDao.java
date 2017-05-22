package com.demo.dao;

import java.util.List;
import java.util.Map;

import com.demo.entity.User;

/**
 * 系统用户
 * 
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 查询用户的所有权限
	 * 
	 * @param userId
	 *            用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	User queryByUserName(String username);

	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
}
