package com.zhonghui.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.zhonghui.tool.bean.EchoChannel;

public interface EchoChannelMapper {
	@Select({"SELECT * FROM `echochannel`"})
	public List<EchoChannel> getEchoChannelAll();
	@Insert({"INSERT INTO `echochannel` (`channelId`,`name`) VALUES (#{channelId,jdbcType=INTEGER},#{name,jdbcType=VARCHAR})"})
	public int insertEchoChannel(EchoChannel echoChannel);
}
