package cn.project.spider_1608.utils;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtils {
	/**
	 * 根据xpath获取指定标签的内容
	 * @param rootNode
	 * @param xpath
	 * @return
	 */
	public static String getText(TagNode tagNode,String xpath){
		
		//title
		String value =null;
		try {
			Object[] nodeObjs = tagNode.evaluateXPath(xpath);
			if (nodeObjs!=null && nodeObjs.length>0) {
				TagNode titleNode = (TagNode)nodeObjs[0];
				//获取里面的内容
				//page.addFiled("title", titleNode.getText().toString());
				value= titleNode.getText().toString();
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String getAttributeByName(TagNode tagNode,String attr,String xpath){
		String value = null;
		Object[] nodeObjs;
		try {
			nodeObjs = tagNode.evaluateXPath(xpath);
			if (nodeObjs!=null&& nodeObjs.length>0) {
				TagNode node = (TagNode)nodeObjs[0];
				value =node.getAttributeByName(attr);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
}
