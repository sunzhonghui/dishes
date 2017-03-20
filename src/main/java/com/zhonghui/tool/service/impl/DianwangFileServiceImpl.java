package com.zhonghui.tool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.bean.DianwangFile;
import com.zhonghui.tool.mapper.DianwangFileMapper;
import com.zhonghui.tool.service.DianwangFileService;
@Service
public class DianwangFileServiceImpl implements DianwangFileService {
	@Autowired
	private DianwangFileMapper dianwangFileMapper;
	@Override
	public List<DianwangFile> selectDianwangFileForUrl(String url) {
		return dianwangFileMapper.selectDianwangFileForUrl(url);
	}

	@Override
	public int insertDiwangFile(DianwangFile dianwang) {
		return dianwangFileMapper.insertDiwangFile(dianwang);
	}

	@Override
	public int updateDiwangFileDownStatus(int id) {
		return dianwangFileMapper.updateDiwangFileDownStatus(id);
	}

	@Override
	public int updateDiwangFile(DianwangFile dianwang) {
		return dianwangFileMapper.updateDiwangFile(dianwang);
	}
	

}
