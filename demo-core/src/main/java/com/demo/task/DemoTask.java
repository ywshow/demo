package com.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.entity.User;
import com.demo.service.UserService;

@Component("demoTask")
public class DemoTask {

	private Logger logger = LoggerFactory.getLogger(DemoTask.class);

	@Autowired
	private UserService userService;

	public void test(String params) {
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		User user = userService.queryObject(1L);
		System.out.println(user.toString());
	}

	public void test2() {
		logger.info("我是不带参数的test2方法，正在被执行");
		System.out.println("我是不带参数的test2方法，正在被执行");
	}
}
