package cn.project.spider_1608;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.management.RuntimeErrorException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.Downloadable;
import cn.project.spider_1608.download.HttpClientDownload;
import cn.project.spider_1608.process.JdProcess;
import cn.project.spider_1608.process.Processable;
import cn.project.spider_1608.repository.QueueRepository;
import cn.project.spider_1608.repository.RedisRepository;
import cn.project.spider_1608.repository.Repository;
import cn.project.spider_1608.store.ConsoleStoreableImpl;
import cn.project.spider_1608.store.Storeable;
import cn.project.spider_1608.utils.SleepUtils;


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
	private Downloadable downloadable = new HttpClientDownload();
	//解析的接口
	private Processable processable;
	//存储接口
	private Storeable storeable = new ConsoleStoreableImpl();
	private Queue<String> queue =new ConcurrentLinkedDeque<String>();
	//设置存储仓库的接口QueueRepository
	private Repository repository = new QueueRepository() ;
	//每个类里面都使用logger日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 启动爬虫
	 */
	public void start() {
		check();
		logger.info("===爬虫开始运行===");
		//爬虫一直运行
		while(true){
//			String url = queue.poll();
			String url =repository.poll();
			if(StringUtils.isNotBlank(url)){
				//下载
				Page page = this.download(url);
				//解析
				this.process(page);
				List<String> urlList = page.getUrlList();
				for (String nexturl : urlList) {
//					System.out.println(nexturl);
//					this.queue.add(nexturl);
					this.repository.add(nexturl);
//					System.out.println(nexturl);
				}
					//优先 把分类的url抓取下来，再抓取分类里面的商品url
					//存储
					if (url.startsWith("http://item.jd.com/")) {
						this.store(page);
		//					repository.addHight(nexturl);
					}
					SleepUtils.sleep(1000);
				}else{
					System.out.println("没有url了。。。");
					SleepUtils.sleep(5000);//5s
				}
		}
		
	}
	/**
	 * 启动前检查
	 */
	private void check() {
		logger.info("开始执行配置检查");
		if (processable==null) {
			String message = "需要设置解析实现类...";
			logger.error(message);
			throw new RuntimeException(message);
		}
		logger.info("==========================配置检查开始===============================");
		logger.info("downloadable的实现类是：{}",downloadable.getClass().getName());
		logger.info("processable的实现类是：{}",processable.getClass().getName());
		logger.info("storeable的实现类是：{}",storeable.getClass().getName());
		logger.info("repository的实现类是：{}",repository.getClass().getName());
		logger.info("===========================配置检查结束==============================");
	
	
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
//		this.queue.add(url);
		this.repository.add(url);
	}
	//Repository的get/set
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
//	
	public static void main(String[] args) {
		Spider spider = new Spider();
//		spider.setDownloadable(new HttpClientDownload());
		spider.setProcessable(new JdProcess());
//		spider.setStoreable(new ConsoleStoreableImpl());
		//"http://list.jd.com/list.html?cat=9987%2C653%2C655&go=0"http://item.jd.com/1861098.html
		String url ="http://list.jd.com/list.html?cat=9987,653,655";
		spider.setSeedUrl(url);
		spider.start();
	}
	

}
