package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.User;
import com.demo.service.UserRoleService;
import com.demo.service.UserService;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;
import com.demo.util.common.ShiroUtils;

/**
 * 系统用户
 * 
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public ResultData list(String username, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		// 查询列表数据
		List<User> userList = userService.queryList(map);
		int total = userService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(userList, total, limit, page);
		return ResultData.ok().put("page", pageUtil);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public ResultData info() {
		return ResultData.ok().put("user", ShiroUtils.getUserId());
	}

	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/password")
	public ResultData password(String password, String newPassword) {
		if (StringUtils.isBlank(newPassword)) {
			return ResultData.error("新密码不为能空");
		}
		// sha256加密
		password = new Sha256Hash(password).toHex();
		// sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();
		// 更新密码
		int count = userService.updatePassword(ShiroUtils.getUserId(), password, newPassword);
		if (count == 0) {
			return ResultData.error("原密码不正确");
		}
		// 退出
		ShiroUtils.logout();
		return ResultData.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public ResultData info(@PathVariable("userId") Long userId) {
		User user = userService.queryObject(userId);
		// 获取用户所属的角色列表
		List<Long> roleIdList = userRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return ResultData.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public ResultData save(@RequestBody User user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return ResultData.error("用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return ResultData.error("密码不能为空");
		}
		userService.save(user);
		return ResultData.ok();
	}

	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public ResultData update(@RequestBody User user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return ResultData.error("用户名不能为空");
		}
		userService.update(user);
		return ResultData.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public ResultData delete(@RequestBody Long[] userIds) {
		if (ArrayUtils.contains(userIds, 1L)) {
			return ResultData.error("系统管理员不能删除");
		}
		if (ArrayUtils.contains(userIds, ShiroUtils.getUserId())) {
			return ResultData.error("当前用户不能删除");
		}
		userService.deleteBatch(userIds);
		return ResultData.ok();
	}
}
