package cn.project.spider_1608.domain;

import java.util.HashMap;
import java.util.Map;

public class Page {
	/**
	 * 原始url
	 */
	private String url;
	/**
	 * 原始页面内容
	 */
	private String content;
	
	/**
	 * 存储商品基本参数信息
	 */
	private Map<String,String> map = new HashMap<String,String>();
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	//url get/set method
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	//map
	public Map<String, String> getMap() {
		return map;
	}

	public void addFiled(String key,String value) {
		this.map.put(key, value);
	}
}
