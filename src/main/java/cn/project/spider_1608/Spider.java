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

public class Spider {

	public void start() {
		
	}
	/**
	 * 下载页面数据
	 * @param url
	 */
	public void download(String url) {
		//获取httpclient
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		
		HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			//获取页面内容
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

}
