package com.zhonghui.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zhonghui.tool.bean.EchoSound;

public interface EchoSoundMapper {

	@Select({"SELECT * from echosound WHERE soundId=#{soundId}"})
	public EchoSound getEchoSoundById(@Param("soundId")Integer soundId);
	
	@Insert({"INSERT INTO `echosound` ( `name`, `length`, `viewCount`, `pic`, `pic100`, `soundId`, `isLike`, `shareCount`, `channelId`, `likeCount`, `commentCount`, `info`, `source`) VALUES(#{name,jdbcType=VARCHAR},#{length,jdbcType=INTEGER},#{viewCount,jdbcType=INTEGER}, #{pic,jdbcType=VARCHAR},#{pic100,jdbcType=VARCHAR},#{soundId,jdbcType=INTEGER},#{isLike,jdbcType=INTEGER},#{shareCount,jdbcType=INTEGER},#{channelId,jdbcType=INTEGER},#{likeCount,jdbcType=INTEGER},#{commentCount,jdbcType=INTEGER},#{info,jdbcType=LONGVARCHAR},#{source,jdbcType=VARCHAR})"})
	public int insertEchoSoundById(EchoSound echoSound);
	
	@Select({"SELECT * from echosound"})
	public List<EchoSound> getEchoSoundAll();
}
