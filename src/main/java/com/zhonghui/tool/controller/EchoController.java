package com.zhonghui.tool.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhonghui.tool.bean.EchoChannel;
import com.zhonghui.tool.processor.DianWangProcessor;
import com.zhonghui.tool.processor.EchoSoundProcessor;
import com.zhonghui.tool.service.EchoChannelService;
import com.zhonghui.tool.service.EchoSoundService;

import us.codecraft.webmagic.Spider;
@RequestMapping(value="/echo")
@Controller
public class EchoController {
	@Autowired
	private EchoSoundProcessor echoSoundProcessor;
	@Autowired
	private DianWangProcessor dianWangProcessor;
	@Autowired
	private EchoChannelService echoChannelService;
	@Autowired
	private EchoSoundService echoSoundService;
	@RequestMapping(value="/catch")
	public String catchChannelEchoSound(){
		List<EchoChannel> channelist=echoChannelService.getEchoChannelAll();
		for(int i=0;i<channelist.size();i++){
		Spider.create(echoSoundProcessor).addUrl("http://www.app-echo.com/channel/"+channelist.get(i).getChannelId()+"?page=1")
        .run();}
		return "test";
	}
	@RequestMapping(value="/casound")
	@ResponseBody
	public Map<String,Object> catchOneEchoSound(String url){
		Pattern pattern = Pattern.compile("^http://www.app-echo.com/sound/\\d+");
		  Matcher matcher = pattern.matcher(url);
		if(!matcher.matches()){	
			Map<String,Object> res=new HashMap<String, Object>();
			res.put("status",-1);
			res.put("msg","不是正确歌曲地址!");
			return res;
		}
		return echoSoundService.catchOneEchoSound(url);
	}
	
	@RequestMapping(value="/gsound")
	public String goCatchEchoSound(){
		return "catchEchoSound";
	}
	@RequestMapping(value="/catch/{channelId}")
	public String catchChannelEchoSoundForChannel(@PathVariable Integer channelId){
		Spider.create(echoSoundProcessor).addUrl("http://www.app-echo.com/channel/"+channelId+"?page=1")
        .run();
		return "test";
	}
	@RequestMapping("/dianwang")
	public String catchDianWang(){
		Spider.create(dianWangProcessor).addUrl("http://ecp.sgcc.com.cn/news_list.jsp?site=global&column_code=014002003&company_id=00&news_name=all").run();
		return "test";
	}
	
}
