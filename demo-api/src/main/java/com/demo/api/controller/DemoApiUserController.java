package com.demo.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.demo.entity.User;
import com.demo.service.UserService;

@Path("/sys/")
@Controller
public class DemoApiUserController {

	@Autowired
	private UserService userService;

	/**
	 * 账户查询
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("findByUserName")
	@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
	public User findByUserName(String username) {
		return userService.queryByUserName(username);
	}

	/**
	 * ID查询
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("findById/"+"{id : \\d+}")
	@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
	public User findById(@PathParam("id") String id) {
		return userService.queryObject(Long.valueOf(id));
	}
}
