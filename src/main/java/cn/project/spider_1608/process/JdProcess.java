package cn.project.spider_1608.process;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.utils.HtmlUtils;
import cn.project.spider_1608.utils.PageUtils;
import cn.project.spider_1608.utils.RevUtils;

public class JdProcess implements Processable {

	@Override
	public void process(Page page) {
		HtmlCleaner htmlCleaner = new HtmlCleaner();
//		String content = page.getContent();
		try {
		TagNode rootNode = htmlCleaner.clean(page.getContent());
			if (page.getUrl().startsWith("http://list.jd.com/list.html")) {
			//先抓下一页
				String nexturl = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"J_topPage\"]/a[2]");
				if (!"javascript:;".equals(nexturl)) {//判断不是最后一页的下一页url，这样指定才不会报空指针
					page.addUrl("http://list.jd.com"+nexturl);
//					可以解析下一页
//					System.out.println("http://list.jd.com"+nexturl);
				}
//			当前页面商品所有url
				Object[] goodurlobjs = rootNode.evaluateXPath("//*[@id=\"plist\"]/ul/li/div/div[1]/a");
				for (Object goodObj: goodurlobjs) {
					TagNode goodNode =(TagNode)goodObj;
					String goodUrl = goodNode.getAttributeByName("href");
					page.addUrl("http:"+goodUrl);
					//可以解析商品所有url
//					System.out.println("http:"+goodUrl);
				}
			}else{
				//商品明细页面
				parseProduct(page, rootNode);
			}
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		
	}
/**
 * 解析商品
 * @param page
 * @param rootNode
 * @throws XPatherException
 */
	private void parseProduct(Page page, TagNode rootNode)
			throws XPatherException {
		//标题
		String title = HtmlUtils.getText(rootNode, "//div[@class='sku-name']");
		page.addField("title", title);
//		System.out.println(title);
		//picture图片地址 
		/*Object[] picpathObjs = rootNode.evaluateXPath("//*[@id=\"spec-img\"]");
		if (picpathObjs!=null && picpathObjs.length>0) {
			TagNode picNode = (TagNode)picpathObjs[0];
			String picUrl = picNode.getAttributeByName("data-origin");
			page.addFiled("picUrl", "http:"+picUrl);
		}*/
		String picpath = HtmlUtils.getAttributeByName(rootNode,"data-origin" ,"//*[@id=\"spec-img\"]");
		page.addField("picpath","http:"+picpath);
		
		//get productID获取商品ID
		String price = getPrice(page);
		page.addField("price",price);
//		System.out.println(price);
		//System.out.println(object.getString("p"));
		
		//规格参数
		JSONArray specjsonArray = getSpec(rootNode);
		page.addField("spec",specjsonArray.toString());
	}
/**
 * 解析规格参数
 * @param rootNode
 * @return
 * @throws XPatherException
 */
	private JSONArray getSpec(TagNode rootNode) throws XPatherException {
		JSONArray specjsonArray = new JSONArray();
		Object[] itemObjs = rootNode.evaluateXPath("//*[@id=\"detail\"]/div[2]/div[2]/div[2]/div");
		for (Object itemObject : itemObjs) {
			TagNode itemNode = (TagNode)itemObject;
			//获取h3标签"主体"
			Object[] h3objs = itemNode.evaluateXPath("/h3");
			if (h3objs!=null && h3objs.length>0) {
				JSONObject h3jsonObj = new JSONObject();
				TagNode h3Node = (TagNode)h3objs[0];
				//System.out.println(h3Node.getText().toString());
				h3jsonObj.put("name", h3Node.getText().toString());
				h3jsonObj.put("value", "");
				specjsonArray.put(h3jsonObj);
			}
			//
			Object[] dtobjs = itemNode.evaluateXPath("/dl/dt");
			Object[] ddobjs = itemNode.evaluateXPath("/dl/dd");
			if (dtobjs!=null && dtobjs.length>0 && ddobjs!=null && ddobjs.length>0) {
				for (int i = 0; i < dtobjs.length; i++) {
					JSONObject dtddjsonObj = new JSONObject();
					TagNode dtNode =(TagNode)dtobjs[i];
					TagNode ddNode =(TagNode)ddobjs[i];
					dtddjsonObj.put("name", dtNode.getText().toString());
					dtddjsonObj.put("value", ddNode.getText().toString());
					specjsonArray.put(dtddjsonObj);
				}
			}
			
		}
		return specjsonArray;
	}
/**
 * 解析价格
 * @param page
 * @return
 */
	private String getPrice(Page page) {
		String url = page.getUrl();
		//pattern正则表达式
		Pattern pattern = Pattern.compile("http://item.jd.com/([0-9]+).html");
		Matcher matcher = pattern.matcher(url);
		String goodsId =null;
		if(matcher.find()){
			goodsId = matcher.group(1);
//			System.out.println(goodsId);
			page.setGoodsid(RevUtils.reverse(goodsId)+"_jd");

		}
		//price,需要分析js请求
		/*Object[] priceObjs = rootNode.evaluateXPath("//span[@class='p-price']/span[2]");
		if (priceObjs!=null && priceObjs.length>0) {
			TagNode priceNode = (TagNode)priceObjs[0];
			System.out.println("price:"+priceNode.getText().toString()+"---");
		}*/
		String priceJson = PageUtils.getContent("http://p.3.cn/prices/mgets?skuIds=J_"+goodsId);
		JSONArray jsonArray = new JSONArray(priceJson);
//		JSONObject object = (JSONObject)jsonArray.get(0);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String price = jsonObject.getString("p");
//		System.out.println(price);
		return price;
	}

}
