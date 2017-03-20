package com.zhonghui.tool.bean;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://ecp.sgcc.com.cn/news_list.jsp?site=global&column_code=014001007&company_id=00&news_name=all&pageNo=2";
		System.out.println(url.matches(".*ecp.sgcc.com.cn/news_list.jsp\\?site=global&column_code=014001007.*"));
		
	}

}
