package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.entity.ConfigItem;

/**
 * 
 * 
 * @author yw
 * @email 576307322@qq.com
 * @date 2017-05-09 11:52:23
 */
public interface ConfigItemService {
	
	ConfigItem queryObject(Long id);
	
	List<ConfigItem> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ConfigItem configItem);
	
	void update(ConfigItem configItem);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
