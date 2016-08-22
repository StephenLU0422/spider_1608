package cn.project.spider_1608;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.download.Downloadable;
import cn.project.spider_1608.utils.PageUtils;


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
			Object[] evaluateXPath = rootNode.evaluateXPath("//div[@class='sku-name']");
			if (evaluateXPath.length>0) {
				TagNode titleNode = (TagNode)evaluateXPath[0];
				//获取里面的内容
				String title = titleNode.getText().toString();
				System.out.println(title);
			}
			
			//picture图片地址 TODO--
			Object[] picpathObjs = rootNode.evaluateXPath("//*[@id=\"spec-img\"]");
			if (picpathObjs!=null && picpathObjs.length>0) {
				TagNode picNode = (TagNode)picpathObjs[0];
				String picUrl = picNode.getAttributeByName("src");
				System.out.println(picUrl);
			}
			//get productID获取商品ID
			String url = page.getUrl();
			//pattern正则表达式
			Pattern pattern = Pattern.compile("http://item.jd.com/([0-9]+).html");
			Matcher matcher = pattern.matcher(url);
			String goodsId =null;
			if(matcher.find()){
				goodsId = matcher.group(1);
				System.out.println(goodsId);
			}
			
			//price,需要分析js请求
			/*Object[] priceObjs = rootNode.evaluateXPath("//span[@class='p-price']/span[2]");
			if (priceObjs!=null && priceObjs.length>0) {
				TagNode priceNode = (TagNode)priceObjs[0];
				System.out.println("price:"+priceNode.getText().toString()+"---");
			}*/
			String priceJson = PageUtils.getContent("http://p.3.cn/prices/mgets?skuIds=J_1861098");
			System.out.println(priceJson);
			JSONArray jsonArray = new JSONArray(priceJson);
			JSONObject object = (JSONObject)jsonArray.get(0);
			System.out.println(object.getString("p"));	
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
