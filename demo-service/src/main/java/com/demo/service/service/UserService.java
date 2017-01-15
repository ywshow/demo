package com.demo.service.service;

import com.demo.entity.Account;
import com.demo.entity.User;

public interface UserService {
	
	public User findByUserId(User instance);

	public Account findByAccountId(Account instance);
}
