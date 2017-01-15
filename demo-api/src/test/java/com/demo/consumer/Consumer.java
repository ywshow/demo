package com.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.entity.User;
import com.demo.service.service.UserService;

public class Consumer {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/consumer.xml"});
        context.start();
        UserService apiUserService = (UserService)context.getBean("userService"); // 获取远程服务代理
        User user = apiUserService.findByUserId(new User()); // 执行远程方法
	}
}
