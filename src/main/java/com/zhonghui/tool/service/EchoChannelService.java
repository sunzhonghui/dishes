package com.zhonghui.tool.service;

import java.util.List;

import com.zhonghui.tool.bean.EchoChannel;

public interface EchoChannelService {
	public List<EchoChannel> getEchoChannelAll();
	public int insertEchoChannel(EchoChannel echoChannel);
}
