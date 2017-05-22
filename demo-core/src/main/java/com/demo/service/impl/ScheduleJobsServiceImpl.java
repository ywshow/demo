package com.demo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.ScheduleJobsDao;
import com.demo.entity.ScheduleJobs;
import com.demo.service.ScheduleJobsService;
import com.demo.util.common.Constant.ScheduleStatus;
import com.demo.util.common.ScheduleUtils;

@Service("scheduleJobService")
public class ScheduleJobsServiceImpl implements ScheduleJobsService {
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ScheduleJobsDao schedulerJobDao;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<ScheduleJobs> scheduleJobList = schedulerJobDao.queryList(new HashMap<String, Object>());
		for (ScheduleJobs scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			// 如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	public ScheduleJobs queryObject(Long jobId) {
		return schedulerJobDao.queryObject(jobId);
	}

	public List<ScheduleJobs> queryList(Map<String, Object> map) {
		return schedulerJobDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return schedulerJobDao.queryTotal(map);
	}

	@Transactional
	public void save(ScheduleJobs scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
		schedulerJobDao.save(scheduleJob);

		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Transactional
	public void update(ScheduleJobs scheduleJob) {
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

		schedulerJobDao.update(scheduleJob);
	}

	@Transactional
	public void deleteBatch(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}

		// 删除数据
		schedulerJobDao.deleteBatch(jobIds);
	}

	public int updateBatch(Long[] jobIds, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jobIds);
		map.put("status", status);
		return schedulerJobDao.updateBatch(map);
	}

	@Transactional
	public void run(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.run(scheduler, queryObject(jobId));
		}
	}

	@Transactional
	public void pause(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}

		updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
	}

	@Transactional
	public void resume(Long[] jobIds) {
		for (Long jobId : jobIds) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		}

		updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
	}

}
