package com.demo.dao;

import java.util.Map;

import com.demo.entity.ScheduleJobs;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:29:57
 */
public interface ScheduleJobsDao extends BaseDao<ScheduleJobs> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
