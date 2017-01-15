package com.demo.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.demo.entity.Account;
import com.demo.entity.User;
import com.demo.service.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	 private final static Logger logger = LoggerFactory.getLogger(UserService.class); 
	public User findByUserId(User instance) {
		System.out.println("********************************");
		return null;
	}

	public Account findByAccountId(Account instance) {
		// TODO Auto-generated method stub
		return null;
	}
}
