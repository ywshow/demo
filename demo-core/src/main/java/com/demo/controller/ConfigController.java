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

import com.demo.entity.Config;
import com.demo.service.ConfigService;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;
import com.demo.util.exception.RRException;

/**
 * 系统配置信息
 * 
 */
@RestController
@RequestMapping("/sys/config")
public class ConfigController extends AbstractController {
	@Autowired
	private ConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public ResultData list(String key, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		// 查询列表数据
		List<Config> configList = sysConfigService.queryList(map);
		int total = sysConfigService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(configList, total, limit, page);

		return ResultData.ok().put("page", pageUtil);
	}

	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public ResultData info(@PathVariable("id") Long id) {
		Config config = sysConfigService.queryObject(id);

		return ResultData.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public ResultData save(@RequestBody Config config) {
		// 参数校验
		verifyForm(config);

		sysConfigService.save(config);

		return ResultData.ok();
	}

	/**
	 * 修改配置
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public ResultData update(@RequestBody Config config) {
		// 参数校验
		verifyForm(config);

		sysConfigService.update(config);

		return ResultData.ok();
	}

	/**
	 * 删除配置
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public ResultData delete(@RequestBody Long[] ids) {
		sysConfigService.deleteBatch(ids);

		return ResultData.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(Config config) {
		if (StringUtils.isBlank(config.getKey())) {
			throw new RRException("参数名不能为空");
		}

		if (StringUtils.isBlank(config.getValue())) {
			throw new RRException("参数值不能为空");
		}
	}
}
