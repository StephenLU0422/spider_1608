package cn.project.spider_1608;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpiderTest {
	@Test
	public void test() throws Exception {
		//假设有spider
		Spider spider = new Spider();
//		spider.start();
		String url ="http://item.jd.com/2569127.html";
		spider.download(url);
		spider.process();
		spider.store();
	}
}
