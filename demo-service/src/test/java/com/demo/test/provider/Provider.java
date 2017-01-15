package com.demo.test.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	public static void main(String[] args) throws IOException {
		  ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/provider.xml"});
	       
	        System.out.println("开始。。。");
	        context.start();
	        context.getBean("userService");
	        System.in.read(); // 按任意键退出  
	}
}
