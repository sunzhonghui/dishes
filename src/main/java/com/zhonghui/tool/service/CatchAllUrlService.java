package com.zhonghui.tool.service;

import java.util.List;

import com.zhonghui.tool.bean.CatchAllUrl;

public interface CatchAllUrlService {
	public List<CatchAllUrl> getCatchAllUrlByUrl(String url); 
	public int inertCatchUrl(CatchAllUrl catchAllurl);
}
