package com.zhonghui.tool.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface TestMapper {
	
	@Select({"select * from echochannel"})
	public List<Map<String,Object>> test();
}
