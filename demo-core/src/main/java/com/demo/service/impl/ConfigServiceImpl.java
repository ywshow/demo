package com.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.dao.ConfigDao;
import com.demo.entity.Config;
import com.demo.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;

	public void save(Config config) {
		configDao.save(config);
	}

	public void update(Config config) {
		configDao.update(config);
	}

	public void updateValueByKey(String key, String value) {
		configDao.updateValueByKey(key, value);
	}

	public void deleteBatch(Long[] ids) {
		configDao.deleteBatch(ids);
	}

	public List<Config> queryList(Map<String, Object> map) {
		return configDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return configDao.queryTotal(map);
	}

	public Config queryObject(Long id) {
		return configDao.queryObject(id);
	}

	public String getValue(String key, String defaultValue) {
		String value = configDao.queryByKey(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	public <T> T getConfigObject(String key, Class<T> clazz) throws Exception {
		String value = getValue(key, null);
		if (StringUtils.isNotBlank(value)) {
			return JSON.parseObject(value, clazz);
		}

		return clazz.newInstance();
	}
}
