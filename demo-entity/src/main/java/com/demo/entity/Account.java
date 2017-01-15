package com.demo.entity;

public class Account extends BaseEntity{

	private String loginNam;
	private String realName;
	private String phoneNumber;

	public String getLoginNam() {
		return loginNam;
	}

	public void setLoginNam(String loginNam) {
		this.loginNam = loginNam;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
