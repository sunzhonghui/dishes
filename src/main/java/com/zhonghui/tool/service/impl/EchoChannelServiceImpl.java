package com.zhonghui.tool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.EchoChannel;
import com.zhonghui.tool.mapper.EchoChannelMapper;
import com.zhonghui.tool.service.EchoChannelService;
@Service
public class EchoChannelServiceImpl implements EchoChannelService {
	@Autowired
	private EchoChannelMapper echoChannelMapper;
	@Override
	public List<EchoChannel> getEchoChannelAll() {
		// TODO Auto-generated method stub
		return echoChannelMapper.getEchoChannelAll();
	}

	@Override
	public int insertEchoChannel(EchoChannel echoChannel) {
		// TODO Auto-generated method stub
		return echoChannelMapper.insertEchoChannel(echoChannel);
	}

}
