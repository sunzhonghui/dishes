package com.zhonghui.tool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.CatchAllUrl;
import com.zhonghui.tool.mapper.CatchAllUrlMapper;
import com.zhonghui.tool.service.CatchAllUrlService;
@Service
public class CatchAllUrlServiceImpl implements CatchAllUrlService {
	@Autowired
	private CatchAllUrlMapper catchAllUrlMapper;
	@Override
	public List<CatchAllUrl> getCatchAllUrlByUrl(String url) {
		// TODO Auto-generated method stub
		return catchAllUrlMapper.getCatchAllUrlByUrl(url);
	}

	@Override
	public int inertCatchUrl(CatchAllUrl catchAllurl) {
		// TODO Auto-generated method stub
		return catchAllUrlMapper.inertCatchUrl(catchAllurl);
	}

}
