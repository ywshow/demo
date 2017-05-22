package com.demo.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author yw
 * @email 576307322@qq.com
 * @date 2017-05-09 11:45:44
 */
public class ConfigItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long pid;
	//
	private String name;
	//key
	private String key;
	//value
	private String value;
	//状态   0：隐藏   1：显示
	private Integer status;
	//备注
	private String remark;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 获取：
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取：key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置：value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：状态   0：隐藏   1：显示
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态   0：隐藏   1：显示
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
