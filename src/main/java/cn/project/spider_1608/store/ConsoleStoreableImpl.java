package cn.project.spider_1608.store;

import cn.project.spider_1608.domain.Page;

public class ConsoleStoreableImpl implements Storeable {

	@Override
	public void store(Page page) {
		System.out.println(page.getUrl()+"--"+page.getMap().get("price"));
	}

}
