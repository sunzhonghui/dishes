package com.zhonghui.tool.processor;

import java.util.List;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.CatchAllUrl;
import com.zhonghui.tool.bean.EchoSound;
import com.zhonghui.tool.service.CatchAllUrlService;
import com.zhonghui.tool.service.EchoSoundService;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
@Service
public class EchoSoundProcessor implements PageProcessor {
	@Autowired
	private CatchAllUrlService catchAllUrlService;
	@Autowired
	private EchoSoundService echoSoundService;
	
	public static final String URL_LIST = ".*www.app-echo.com/channel/\\d+\\?page.*";

    public static final String URL_POST = "/sound/\\w";

    private Site site = Site
            .me()
            .setDomain("www.app-echo.com")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36")
            .addCookie("echo_language","0fa769e85f49c8f39f1a51b419d5ec98c7821fcdb7666236b7c498a20cee27fea%3A2%3A%7Bi%3A0%3Bs%3A13%3A%22echo_language%22%3Bi%3A1%3Bs%3A2%3A%22cn%22%3B%7D")
            .addCookie("_csrf","e00ded3f2135a262f3b508509b0784f2c5c534863f0c3f9425407cbe66323039a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22bO82enXumtk2WAsTiS0o6hFWljPohsfn%22%3B%7D")
            .addCookie("PHPSESSID","3cl16igkq8n9ufbsecrjaq7l27").setHttpProxy(new HttpHost("128.199.166.84",8080));

    public void process(Page page) {
    	CatchAllUrl catchAllUrl=new CatchAllUrl();
    	catchAllUrl.setUrl(page.getUrl().toString());
    	catchAllUrlService.inertCatchUrl(catchAllUrl);
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
        	List<String> urlist=page.getHtml().xpath("//ul[@class=\"chan-exppopular-list\"]").links().all();
        	for(int i=0;i<urlist.size();i++){
        		String isoundId=urlist.get(i);
        		isoundId=isoundId.substring(isoundId.lastIndexOf("/")+1);
        		if(urlist.get(i).indexOf("sound")>0&&echoSoundService.getEchoSoundById(Integer.parseInt(isoundId))==null){
        		page.addTargetRequest(urlist.get(i));
        		}
        	}
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else {
//            page.putField("title", page.getHtml().xpath("//div[@class='single-title clearfix']/h1/text()"));
            String mp3Js=page.getHtml().xpath("//main[@class='main-part clearfix']/script[1]").get();
            mp3Js=mp3Js.substring(9, mp3Js.indexOf("$(function()"));
            String[] soundInfo=mp3Js.split(";");
            soundInfo[2]=soundInfo[2].substring(soundInfo[2].indexOf("{"),soundInfo[2].lastIndexOf("}")+1);//page_sound_obj
            JSONObject jsb=null;
            try {
				jsb=(JSONObject) JSONValue.parseStrict(soundInfo[2]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //歌曲信息
//            page.putField("soundName",jsb.get("name").toString().trim());
//            page.putField("soundLength",jsb.get("length").toString().trim());
//            page.putField("soundViewCount",jsb.get("view_count").toString().trim());
//            page.putField("soundPic",jsb.get("pic").toString().trim());
//            page.putField("soundPic100",jsb.get("pic_100").toString().trim());
//            page.putField("soundId",jsb.get("id").toString().trim());
//            page.putField("soundCreatetime",jsb.get("create_time").toString().trim());
//            page.putField("soundIslike",jsb.get("is_like").toString().trim());
//            page.putField("soundShareCount",jsb.get("share_count").toString().trim());
//            page.putField("soundChannelId",jsb.get("channel_id").toString().trim());
//            page.putField("soundLikeCount",jsb.get("like_count").toString().trim());
//            page.putField("soundCommentCount",jsb.get("comment_count").toString().trim());
//            page.putField("soundInfo",jsb.get("info").toString().trim());
//            page.putField("soundSource",jsb.get("source").toString().trim());
            EchoSound es=new EchoSound();
            es.setChannelId(Integer.parseInt(jsb.get("channel_id").toString().trim()));
            es.setCommentCount(Integer.parseInt(jsb.get("comment_count").toString().trim()));
            es.setInfo(jsb.get("info").toString().trim());
            es.setIsLike(Integer.parseInt(jsb.get("is_like").toString().trim()));
            es.setLength(Integer.parseInt(jsb.get("length").toString().trim()));
            es.setLikeCount(Integer.parseInt(jsb.get("like_count").toString().trim()));
            es.setName(jsb.get("name").toString().trim());
            es.setPic(jsb.get("pic").toString().trim());
            es.setPic100(jsb.get("pic_100").toString().trim());
            es.setShareCount(Integer.parseInt(jsb.get("share_count").toString().trim()));
            es.setSoundId(Integer.parseInt(jsb.get("id").toString().trim()));
            es.setSource(jsb.get("source").toString().trim());
            es.setViewCount(Integer.parseInt(jsb.get("view_count").toString().trim()));
            echoSoundService.insertEchoSoundById(es);
//            //分类信息
//            try {
//				jsb=(JSONObject) JSONValue.parseStrict(jsb.get("channel").toString());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            page.putField("channelId",jsb.get("id").toString().trim());
//            page.putField("channelName",jsb.get("name").toString().trim());
//            page.putField("channelPic",jsb.get("pic").toString().trim());
//            page.putField("channelInfo",jsb.get("info").toString().trim());
//            page.putField("channelType",jsb.get("type").toString().trim());
//            page.putField("channelTagId",jsb.get("tag_id").toString().trim());
//            page.putField("channelSoundCount",jsb.get("sound_count").toString().trim());
//            page.putField("channelFollowCount",jsb.get("follow_count").toString().trim());
//            page.putField("channelLikeCount",jsb.get("like_count").toString().trim());
//            page.putField("channelShareCount",jsb.get("share_count").toString().trim());
//            page.putField("channelUserId",jsb.get("user_id").toString().trim());
//            page.putField("channelUpdateUserId",jsb.get("update_user_id").toString().trim());
//            page.putField("channellist_order",jsb.get("list_order").toString().trim());
//            page.putField("channelcreate_time",jsb.get("create_time").toString().trim());
//            page.putField("channelupdate_time",jsb.get("update_time").toString().trim());
//            page.putField("channelstatus",jsb.get("status").toString().trim());
//            page.putField("channeldesp",jsb.get("desp").toString().trim());
//            page.putField("channelpic_100",jsb.get("pic_100").toString().trim());
//            page.putField("channelpic_200",jsb.get("pic_200").toString().trim());
//            page.putField("channelpic_500",jsb.get("pic_500").toString().trim());
//            page.putField("channelpic_640",jsb.get("pic_640").toString().trim());
//            page.putField("channelpic_750",jsb.get("pic_750").toString().trim());
//            page.putField("channelpic_1080",jsb.get("pic_1080").toString().trim());
            
        }
    }
    public Site getSite() {
        return site;
    }

    
}