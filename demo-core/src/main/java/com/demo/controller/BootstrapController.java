package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;

@Controller
@RequestMapping("/sys/bootstrap")
public class BootstrapController {

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:bootstrap:list")
	public ResultData list(String tableName, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		PageUtils pageUtil = new PageUtils(list, 0, limit, page);
		
		return ResultData.ok().put("page", pageUtil);
	}
}
