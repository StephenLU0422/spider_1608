package cn.project.spider_1608.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueRepository implements Repository {
	//低优先级队列
	Queue<String> lowqueue = new ConcurrentLinkedQueue<String>();
	//高优先级队列
	Queue<String> hightqueue = new ConcurrentLinkedQueue<String>();
	@Override
	public String poll() {
		//先从高优先级队列取
		String url = hightqueue.poll();
		//再低优先级队列取数据
		if (url==null) {
			url = lowqueue.poll();
		}
		return url;
	}

	@Override
	public void add(String nexturl) {
		this.lowqueue.add(nexturl);
	}

	@Override
	public void addHight(String nexturl) {
		this.hightqueue.add(nexturl);
	}

}
