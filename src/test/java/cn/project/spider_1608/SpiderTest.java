package cn.project.spider_1608;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.HttpClientDownload;
import cn.project.spider_1608.process.JdProcess;
import cn.project.spider_1608.store.ConsoleStoreableImpl;

public class SpiderTest {
	@Test
	public void test() throws Exception {
		//假设有spider
		Spider spider = new Spider();
//		spider.start();
		//set HttpClientdownload
		spider.setDownloadable(new HttpClientDownload());
		//set JdProcess method
		spider.setProcessable(new JdProcess());
		spider.setStoreable(new ConsoleStoreableImpl());
		String url ="http://item.jd.com/1861098.html";
		Page page = spider.download(url);
		spider.process(page);
		System.out.println(page.getMap().get("spec"));
		spider.store(page);
	}
}
