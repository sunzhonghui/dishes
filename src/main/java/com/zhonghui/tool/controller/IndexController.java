package com.zhonghui.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@RequestMapping("/personalRegister")
	public String goPersonalRegister(){
		return "personalRegister";
	}
	@RequestMapping("/doRecharge")
	public String goDoRecharge(){
		return "recharge";
	}
}
