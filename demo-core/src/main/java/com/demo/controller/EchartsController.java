package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.util.common.ResultData;

@RequestMapping("/echarts/")
@Controller
public class EchartsController {

	@RequestMapping(value="test",method = RequestMethod.GET)
	@ResponseBody
	public ResultData test(){
		System.out.println("000000000000000000000000");
		return ResultData.ok();
	}
	
}
