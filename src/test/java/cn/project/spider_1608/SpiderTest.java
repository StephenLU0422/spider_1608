package cn.project.spider_1608;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.HttpClientDownload;

public class SpiderTest {
	@Test
	public void test() throws Exception {
		//假设有spider
		Spider spider = new Spider();
//		spider.start();
		spider.setDownloadable(new HttpClientDownload());
		String url ="http://item.jd.com/2569127.html";
		Page page = spider.download(url);
		System.out.println(page.getContent());
		spider.process(page);
		spider.store(page);
	}
}
