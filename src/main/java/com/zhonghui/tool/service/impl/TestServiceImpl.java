package com.zhonghui.tool.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhonghui.tool.mapper.TestMapper;
import com.zhonghui.tool.service.TestService;
@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper testMapper;
	@Override
	public List<Map<String, Object>> testList() {
		// TODO Auto-generated method stub
		return testMapper.test();
	}

}
