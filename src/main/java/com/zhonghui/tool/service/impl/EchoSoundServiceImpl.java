package com.zhonghui.tool.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.EchoSound;
import com.zhonghui.tool.mapper.EchoSoundMapper;
import com.zhonghui.tool.processor.HttpTool;
import com.zhonghui.tool.service.EchoSoundService;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;
@Service
public class EchoSoundServiceImpl implements EchoSoundService {
	@Autowired
	private EchoSoundMapper echoSoundMapper;
	@Override
	public EchoSound getEchoSoundById(Integer soundId) {
		// TODO Auto-generated method stub
		return echoSoundMapper.getEchoSoundById(soundId);
	}

	@Override
	public int insertEchoSoundById(EchoSound echoSound) {
		// TODO Auto-generated method stub
		return echoSoundMapper.insertEchoSoundById(echoSound);
	}

	@Override
	public List<EchoSound> getEchoSoundAll() {
		// TODO Auto-generated method stub
		return echoSoundMapper.getEchoSoundAll();
	}

	@Override
	public Map<String, Object> catchOneEchoSound(String url) {
		// TODO Auto-generated method stub
		Map<String,Object> res=new HashMap<String,Object>();
		EchoSound esa=this.getEchoSoundById(Integer.parseInt(url.substring(url.lastIndexOf("/")+1)));
		if(esa!=null){
			res.put("status",1);
			  res.put("msg",esa);
			  return res;
		}
		EchoSound es=new EchoSound();
		try {
		 Document doc=HttpTool.urlToDoc(url);
		 res.put("html",doc.html());
		 String html=doc.getElementsByAttributeValue("class","main-part clearfix").html();
		  html=html.substring(html.indexOf("page_sound_obj"),html.indexOf("};")+1);
		  html=html.substring(html.indexOf("{"));
		  JSONObject jsb=null;
				jsb=(JSONObject) JSONValue.parseStrict(html);
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
          this.insertEchoSoundById(es);
          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return res;
		}
          
          res.put("status",1);
		  res.put("msg",es);
		return res;
	}
	public static void main(String[] args) {
		Document doc=HttpTool.urlToDoc("http://music.163.com/weapi/feedback/weblog?csrf_token=b289f74302df1a867de4ef3fe312c2c1");
		System.out.println(doc.html());
	}

}
