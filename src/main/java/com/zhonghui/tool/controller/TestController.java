package com.zhonghui.tool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhonghui.tool.bean.CatchAllUrl;
import com.zhonghui.tool.bean.EchoSound;
import com.zhonghui.tool.processor.EchoSoundProcessor;
import com.zhonghui.tool.service.CatchAllUrlService;
import com.zhonghui.tool.service.EchoChannelService;
import com.zhonghui.tool.service.EchoSoundService;
import com.zhonghui.tool.service.TestService;

import us.codecraft.webmagic.Spider;

@Controller
public class TestController {
	@Autowired 
	TestService testService;
	@Autowired
	private CatchAllUrlService catchAllUrlService;
	@Autowired
	private EchoChannelService echoChannelService;
	@Autowired
	private EchoSoundService echoSoundService;
	@Autowired
	private EchoSoundProcessor echoSoundProcessor;
	
	@RequestMapping(value="/test")
	public String test() {
		// TODO Auto-generated constructor stub
//		CatchAllUrl catchAllurl=new CatchAllUrl();
//		catchAllurl.setUrl("www.baidu.com");;
//		catchAllUrlService.inertCatchUrl(catchAllurl);
//		List<CatchAllUrl> a=catchAllUrlService.getCatchAllUrlByUrl("www.baidu.com");
//		EchoSound  EchoSound =echoSoundService.getEchoSoundById(1259599);
//		List<EchoSound> aa=echoSoundService.getEchoSoundAll();
//		echoSoundService.insertEchoSoundById(EchoSound);
		Spider.create(echoSoundProcessor).addUrl("http://www.app-echo.com/channel/1155?page=1")
        .run();
		return "test";
	}
	@RequestMapping(value="/mapper")
	@ResponseBody
	public List<Map<String,Object>> mapper(){
		
		return testService.testList();
	}
}
