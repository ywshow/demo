package com.demo.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.ScheduleJobLogDao;
import com.demo.entity.ScheduleJobLog;
import com.demo.service.ScheduleJobLogService;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;
	
	public ScheduleJobLog queryObject(Long jobId) {
		return scheduleJobLogDao.queryObject(jobId);
	}

	public List<ScheduleJobLog> queryList(Map<String, Object> map) {
		return scheduleJobLogDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogDao.queryTotal(map);
	}

	public void save(ScheduleJobLog log) {
		scheduleJobLogDao.save(log);
	}

}
