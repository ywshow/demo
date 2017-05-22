package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.entity.ScheduleJobs;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface ScheduleJobsService {

	/**
	 * 根据ID，查询定时任务
	 */
	ScheduleJobs queryObject(Long jobId);
	
	/**
	 * 查询定时任务列表
	 */
	List<ScheduleJobs> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务
	 */
	void save(ScheduleJobs scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobs scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
