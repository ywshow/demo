package com.demo.dao;

import java.util.List;

import com.demo.entity.Menu;

/**
 * 菜单管理
 * 
 */
public interface MenuDao extends BaseDao<Menu> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<Menu> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<Menu> queryNotButtonList();
}
