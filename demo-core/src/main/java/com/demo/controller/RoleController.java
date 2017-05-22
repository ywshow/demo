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

import com.demo.entity.Role;
import com.demo.service.RoleMenuService;
import com.demo.service.RoleService;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;

/**
 * 角色管理
 * 
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuService roleMenuService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public ResultData list(String roleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		// 查询列表数据
		List<Role> list = roleService.queryList(map);
		int total = roleService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return ResultData.ok().put("page", pageUtil);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public ResultData select() {
		// 查询列表数据
		List<Role> list = roleService.queryList(new HashMap<String, Object>());
		return ResultData.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public ResultData info(@PathVariable("roleId") Long roleId) {
		Role role = roleService.queryObject(roleId);
		// 查询角色对应的菜单
		List<Long> menuIdList = roleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		return ResultData.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public ResultData save(@RequestBody Role role) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return ResultData.error("角色名称不能为空");
		}
		roleService.save(role);
		return ResultData.ok();
	}

	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public ResultData update(@RequestBody Role role) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return ResultData.error("角色名称不能为空");
		}
		roleService.update(role);
		return ResultData.ok();
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public ResultData delete(@RequestBody Long[] roleIds) {
		roleService.deleteBatch(roleIds);
		return ResultData.ok();
	}
}
