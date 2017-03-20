package com.zhonghui.tool.service;

import java.util.List;
import java.util.Map;

import com.zhonghui.tool.bean.EchoSound;

public interface EchoSoundService {
	public EchoSound getEchoSoundById(Integer soundId);
	public int insertEchoSoundById(EchoSound echoSound);
	public List<EchoSound> getEchoSoundAll();
	public Map<String, Object> catchOneEchoSound(String url);
}
