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
import cn.project.spider_1608.utils.PageUtils;

public class HttpClientDownload implements Downloadable {

	@Override
	public Page download(String url) {
		Page page = new Page();
		String content = PageUtils.getContent(url);
		page.setContent(content);
		return page;
		
	}

}
