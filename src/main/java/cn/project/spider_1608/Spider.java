package cn.project.spider_1608;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.Downloadable;

public class Spider {
	
	//注入接口
	private Downloadable downloadable;

	public void start() {
		
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
	 * 解析网页内容
	 * @param page
	 */
	public void process(Page page) {
	}
	
	/**
	 * store web content
	 * @param page
	 */
	public void store(Page page) {
		
	}
	//Downloadable impl
	public Downloadable getDownloadable() {
		return downloadable;
	}
	public void setDownloadable(Downloadable downloadable) {
		this.downloadable = downloadable;
	}
	

}
