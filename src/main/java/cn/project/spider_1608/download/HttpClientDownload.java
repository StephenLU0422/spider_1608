package cn.project.spider_1608.download;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.utils.PageUtils;

public class HttpClientDownload implements Downloadable {

	@Override
	public Page download(String url) {
		Page page = new Page();
		String content = PageUtils.getContent(url);
		page.setContent(content);
		page.setUrl(url);
		return page;
		
	}

}
