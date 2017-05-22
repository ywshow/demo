package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.ScheduleJobLog;
import com.demo.service.ScheduleJobLogService;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;

/**
 * 定时任务日志
 * 
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public ResultData list(Integer page, Integer limit, Integer jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<ScheduleJobLog> jobList = scheduleJobLogService.queryList(map);
		int total = scheduleJobLogService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(jobList, total, limit, page);
		
		return ResultData.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public ResultData info(@PathVariable("logId") Long logId){
		ScheduleJobLog log = scheduleJobLogService.queryObject(logId);
		
		return ResultData.ok().put("log", log);
	}
}
