package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Menu;
import com.demo.service.MenuService;
import com.demo.util.common.Constant.MenuType;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;
import com.demo.util.common.ShiroUtils;
import com.demo.util.exception.RRException;

/**
 * 系统菜单
 * 
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public ResultData list(Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		// 查询列表数据
		List<Menu> menuList = menuService.queryList(map);
		int total = menuService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(menuList, total, limit, page);
		return ResultData.ok().put("page", pageUtil);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public ResultData select() {
		// 查询列表数据
		List<Menu> menuList = menuService.queryNotButtonList();
		// 添加顶级菜单
		Menu root = new Menu();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		return ResultData.ok().put("menuList", menuList);
	}

	/**
	 * 角色授权菜单
	 */
	@RequestMapping("/perms")
	@RequiresPermissions("sys:menu:perms")
	public ResultData perms() {
		// 查询列表数据
		List<Menu> menuList = menuService.queryList(new HashMap<String, Object>());
		return ResultData.ok().put("menuList", menuList);
	}

	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public ResultData info(@PathVariable("menuId") Long menuId) {
		Menu menu = menuService.queryObject(menuId);
		return ResultData.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public ResultData save(@RequestBody Menu menu) {
		// 数据校验
		verifyForm(menu);
		menuService.save(menu);
		return ResultData.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public ResultData update(@RequestBody Menu menu) {
		// 数据校验
		verifyForm(menu);
		menuService.update(menu);
		return ResultData.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public ResultData delete(@RequestBody Long[] menuIds) {
		for (Long menuId : menuIds) {
			if (menuId.longValue() <= 28) {
				return ResultData.error("系统菜单，不能删除");
			}
		}
		menuService.deleteBatch(menuIds);
		return ResultData.ok();
	}

	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/user")
	public ResultData user() {
		List<Menu> menuList = menuService.getUserMenuList(ShiroUtils.getUserId());
		return ResultData.ok().put("menuList", menuList);
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(Menu menu) {
		if (StringUtils.isBlank(menu.getName())) {
			throw new RRException("菜单名称不能为空");
		}
		if (menu.getParentId() == null) {
			throw new RRException("上级菜单不能为空");
		}
		// 菜单
		if (menu.getType() == MenuType.MENU.getValue()) {
			if (StringUtils.isBlank(menu.getUrl())) {
				throw new RRException("菜单URL不能为空");
			}
		}
		// 上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if (menu.getParentId() != 0) {
			Menu parentMenu = menuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		// 目录、菜单
		if (menu.getType() == MenuType.CATALOG.getValue() || menu.getType() == MenuType.MENU.getValue()) {
			if (parentType != MenuType.CATALOG.getValue()) {
				throw new RRException("上级菜单只能为目录类型");
			}
			return;
		}
		// 按钮
		if (menu.getType() == MenuType.BUTTON.getValue()) {
			if (parentType != MenuType.MENU.getValue()) {
				throw new RRException("上级菜单只能为菜单类型");
			}
			return;
		}
	}
}
