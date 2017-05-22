package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.entity.Menu;


/**
 * 菜单管理
 * 
 */
public interface MenuService {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<Menu> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<Menu> getUserMenuList(Long userId);
	
	
	/**
	 * 查询菜单
	 */
	Menu queryObject(Long menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<Menu> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(Menu menu);
	
	/**
	 * 修改
	 */
	void update(Menu menu);
	
	/**
	 * 删除
	 */
	void deleteBatch(Long[] menuIds);
}
