package cn.project.spider_1608.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {
	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	/**
	 * 根据url获取页面内容
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		String content = "";
		//获取httpclient
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		
		HttpGet request = new HttpGet(url);
		try {
			//获取当前时间戳
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = client.execute(request);
			//获取页面内容
			HttpEntity entity = response.getEntity();
			//打印页面内容
			content = EntityUtils.toString(entity);
//			logger.info("页面下载成功:{},消耗时间(/ms):{}",url,System.currentTimeMillis()-start_time);
		} catch (Exception e) {
//			logger.info("页面下载失败,url:{}",url);
			e.printStackTrace();
		} 
		return content;
	}
}
