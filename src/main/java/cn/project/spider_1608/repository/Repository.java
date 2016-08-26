package cn.project.spider_1608.repository;

public interface Repository {

	String poll();

	void add(String nexturl);

	void addHight(String nexturl);

}
