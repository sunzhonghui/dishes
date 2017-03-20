package com.zhonghui.tool.bean;

import org.joda.time.DateTime;

public class DianwangFile {
	private Integer id;
	private String title;
	private String url;
	private DateTime createTime;
	private Integer isDownload;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(Integer isDownload) {
		this.isDownload = isDownload;
	}
	
}
