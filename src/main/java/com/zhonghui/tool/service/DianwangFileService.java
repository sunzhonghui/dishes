package com.zhonghui.tool.service;

import java.util.List;

import com.zhonghui.tool.bean.DianwangFile;

public interface DianwangFileService {
	public List<DianwangFile> selectDianwangFileForUrl(String url);
	public int insertDiwangFile(DianwangFile dianwang);
	public int updateDiwangFileDownStatus(int id);
	public int updateDiwangFile(DianwangFile dianwang);
}
