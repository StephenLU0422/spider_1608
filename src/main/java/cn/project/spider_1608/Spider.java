package cn.project.spider_1608;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.Downloadable;
import cn.project.spider_1608.process.Processable;
import cn.project.spider_1608.store.Storeable;


public class Spider {
	
	//注入接口
	private Downloadable downloadable;
	//解析的接口
	private Processable processable;
	//存储接口
	private Storeable storeable;

	public void start() {
	
		
	}
	/**
	 * 解析页面数据
	 */
	public void process() {
		
	}
	/**
	 * 存储页面
	 */
	public void store() {
	
	
	}
	
	/**
	 * 下载页面数据
	 * @param url
	 * @return 
	 */
	public Page download(String url) {
		
		Page page = this.downloadable.download(url);
		return page;
	}
	
	/**
	 * 解析网页内容
	 * @param page
	 */
	public void process(Page page) {
		this.processable.process(page);
		
	}
	
	/**
	 * store web content
	 * @param page
	 */
	public void store(Page page) {
		this.storeable.store(page);
	}
	//Downloadable impl
	public Downloadable getDownloadable() {
		return downloadable;
	}
	public void setDownloadable(Downloadable downloadable) {
		this.downloadable = downloadable;
	}
	//Processable 实现类
	public Processable getProcessable() {
		return processable;
	}
	public void setProcessable(Processable processable) {
		this.processable = processable;
		
	}
	//Storeables实现类
	public Storeable getStoreable() {
		return storeable;
	}
	public void setStoreable(Storeable storeable) {
		this.storeable = storeable;
	}
	
	

}
