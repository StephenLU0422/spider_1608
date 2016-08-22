package cn.project.spider_1608;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

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
		String content = page.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		//title
		try {
			Object[] evaluateXPath = rootNode.evaluateXPath("/html/body/div[5]/div/div[2]/div[1]");
			if (evaluateXPath.length>0) {
				TagNode titleNode = (TagNode)evaluateXPath[0];
				
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
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
