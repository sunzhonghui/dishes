package com.zhonghui.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zhonghui.tool.bean.CatchAllUrl;

public interface CatchAllUrlMapper {
	
	/**
	 * 根据url查找已经抓取的链接
	 * @param url
	 * @return
	 */
	@Select({"SELECT * from catchallurl where url=#{url,jdbcType=VARCHAR}"})
	public List<CatchAllUrl> getCatchAllUrlByUrl(@Param("url")String url);
	
	@Insert({"INSERT INTO `catchallurl` (`url`) VALUES (#{url,jdbcType=VARCHAR})"})
	public int inertCatchUrl(CatchAllUrl catchAllurl);
}
