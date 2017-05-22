package com.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.MenuDao;
import com.demo.entity.Menu;
import com.demo.service.MenuService;
import com.demo.service.UserService;
import com.demo.util.common.Constant.MenuType;


@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private UserService userService;
	
	public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<Menu> menuList = menuDao.queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<Menu> userMenuList = new ArrayList<Menu>();
		for(Menu menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	public List<Menu> queryNotButtonList() {
		return menuDao.queryNotButtonList();
	}

	public List<Menu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == 1){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = userService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	public Menu queryObject(Long menuId) {
		return menuDao.queryObject(menuId);
	}

	public List<Menu> queryList(Map<String, Object> map) {
		return menuDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return menuDao.queryTotal(map);
	}

	public void save(Menu menu) {
		menuDao.save(menu);
	}

	public void update(Menu menu) {
		menuDao.update(menu);
	}

	@Transactional
	public void deleteBatch(Long[] menuIds) {
		menuDao.deleteBatch(menuIds);
	}
	
	/**
	 * 获取所有菜单列表
	 */
	private List<Menu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<Menu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList){
		List<Menu> subMenuList = new ArrayList<Menu>();
		
		for(Menu entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
