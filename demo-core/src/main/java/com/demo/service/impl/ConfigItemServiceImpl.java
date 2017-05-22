package com.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.ConfigItemDao;
import com.demo.entity.ConfigItem;
import com.demo.service.ConfigItemService;

@Service("configItemService")
public class ConfigItemServiceImpl implements ConfigItemService {
	@Autowired
	private ConfigItemDao configItemDao;

	public ConfigItem queryObject(Long id) {
		return configItemDao.queryObject(id);
	}

	public List<ConfigItem> queryList(Map<String, Object> map) {
		return configItemDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return configItemDao.queryTotal(map);
	}

	public void save(ConfigItem sysConfigItem) {
		configItemDao.save(sysConfigItem);
	}

	public void update(ConfigItem sysConfigItem) {
		configItemDao.update(sysConfigItem);
	}

	public void delete(Long id) {
		configItemDao.delete(id);
	}

	public void deleteBatch(Long[] ids) {
		configItemDao.deleteBatch(ids);
	}

}
