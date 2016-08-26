package cn.project.spider_1608;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.httpclient.HttpClient;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.Downloadable;
import cn.project.spider_1608.download.HttpClientDownload;
import cn.project.spider_1608.process.JdProcess;
import cn.project.spider_1608.process.Processable;
import cn.project.spider_1608.store.ConsoleStoreableImpl;
import cn.project.spider_1608.store.Storeable;


public class Spider {
	
	/**
	 * 提取接口的步骤
	 * 1：定义接口
	 * 2：写实现类
	 * 3：在spider中对接口提供set方法
	 * 4：在使用爬虫时给接口注入实现类
	 * 
	 */
	//注入接口
	private Downloadable downloadable;
	//解析的接口
	private Processable processable;
	//存储接口
	private Storeable storeable;
	private Queue <String> queue =new ConcurrentLinkedDeque<String>();

	public void start() {
		//爬虫一直运行
		while(true){
			String url = queue.poll();
			//下载
			Page page = this.download(url);
			//解析
			this.process(page);
			List<String> urlList = page.getUrlList();
			for (String nexurl : urlList) {
				queue.add(nexurl);
			}
			//存储
			this.store(page);
		}
		
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
	//set入口url
	public void setSeedUrl(String url) {
		this.queue.add(url);
	}
	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.setDownloadable(new HttpClientDownload());
		spider.setProcessable(new JdProcess());
		spider.setStoreable(new ConsoleStoreableImpl());
		String url ="http://list.jd.com/list.html?cat=9987,653,655";
		spider.setSeedUrl(url);
		spider.start();
	}
	

}
