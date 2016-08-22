package cn.project.spider_1608.process;

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

public class JdProcess implements Processable {

	@Override
	public void process(Page page) {
		String content = page.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		try {
			//标题
			String title = HtmlUtils.getText(rootNode, "//div[@class='sku-name']");
			page.addField("title", title);
			
			//picture图片地址 
			/*Object[] picpathObjs = rootNode.evaluateXPath("//*[@id=\"spec-img\"]");
			if (picpathObjs!=null && picpathObjs.length>0) {
				TagNode picNode = (TagNode)picpathObjs[0];
				String picUrl = picNode.getAttributeByName("data-origin");
				page.addFiled("picUrl", "http:"+picUrl);
			}*/
			String picpath = HtmlUtils.getAttributeByName(rootNode,"data-origin" ,"//*[@id=\"spec-img\"]");
			page.addField("picpath", "http:"+picpath);
			
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
			String priceJson = PageUtils.getContent("http://p.3.cn/prices/mgets?skuIds=J_"+goodsId);
			System.out.println(priceJson);
			JSONArray jsonArray = new JSONArray(priceJson);
			JSONObject object = (JSONObject)jsonArray.get(0);
			page.addField("price", object.getString("p"));
			
			//System.out.println(object.getString("p"));
			
			//规格参数
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
			page.addField("spec", specjsonArray.toString());
			
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		
	}

}