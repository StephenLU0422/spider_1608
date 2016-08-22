package cn.project.spider_1608.download;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.project.spider_1608.domain.Page;

public class HttpClientDownload implements Downloadable {

	@Override
	public Page download(String url) {
Page page = new Page();
		
		//获取httpclient
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		
		HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			//获取页面内容
			HttpEntity entity = response.getEntity();
			//set to Page
			page.setContent(EntityUtils.toString(entity));
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
		
	}

}
