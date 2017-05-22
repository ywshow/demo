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

import com.demo.entity.ScheduleJobs;
import com.demo.service.ScheduleJobsService;
import com.demo.util.common.PageUtils;
import com.demo.util.common.ResultData;
import com.demo.util.exception.RRException;

/**
 * 定时任务
 * 
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobsController {
	@Autowired
	private ScheduleJobsService scheduleJobService;

	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public ResultData list(String beanName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beanName", beanName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		// 查询列表数据
		List<ScheduleJobs> jobList = scheduleJobService.queryList(map);
		int total = scheduleJobService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(jobList, total, limit, page);

		return ResultData.ok().put("page", pageUtil);
	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public ResultData info(@PathVariable("jobId") Long jobId) {
		ScheduleJobs schedule = scheduleJobService.queryObject(jobId);

		return ResultData.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public ResultData save(@RequestBody ScheduleJobs scheduleJob) {
		// 数据校验
		verifyForm(scheduleJob);

		scheduleJobService.save(scheduleJob);

		return ResultData.ok();
	}

	/**
	 * 修改定时任务
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public ResultData update(@RequestBody ScheduleJobs scheduleJob) {
		// 数据校验
		verifyForm(scheduleJob);

		scheduleJobService.update(scheduleJob);

		return ResultData.ok();
	}

	/**
	 * 删除定时任务
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public ResultData delete(@RequestBody Long[] jobIds) {
		scheduleJobService.deleteBatch(jobIds);

		return ResultData.ok();
	}

	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public ResultData run(@RequestBody Long[] jobIds) {
		scheduleJobService.run(jobIds);

		return ResultData.ok();
	}

	/**
	 * 暂停定时任务
	 */
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public ResultData pause(@RequestBody Long[] jobIds) {
		scheduleJobService.pause(jobIds);

		return ResultData.ok();
	}

	/**
	 * 恢复定时任务
	 */
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public ResultData resume(@RequestBody Long[] jobIds) {
		scheduleJobService.resume(jobIds);

		return ResultData.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(ScheduleJobs scheduleJob) {
		if (StringUtils.isBlank(scheduleJob.getBeanName())) {
			throw new RRException("bean名称不能为空");
		}

		if (StringUtils.isBlank(scheduleJob.getMethodName())) {
			throw new RRException("方法名称不能为空");
		}

		if (StringUtils.isBlank(scheduleJob.getCronExpression())) {
			throw new RRException("cron表达式不能为空");
		}
	}
}
