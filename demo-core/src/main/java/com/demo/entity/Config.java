package com.demo.entity;

import java.util.List;

/**
 * 系统配置信息
 * 
 */
public class Config {
	private Long id;
	/**
	 * 参数代码
	 */
	private String key;
	/**
	 * 参数值
	 */
	private String value;
	private String remark;
	/**
	 * 参数名
	 */
	private String name;

	public boolean status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<ConfigItem> getConfigItemList() {
		return configItemList;
	}

	public void setConfigItemList(List<ConfigItem> configItemList) {
		this.configItemList = configItemList;
	}

	public List<ConfigItem> configItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
