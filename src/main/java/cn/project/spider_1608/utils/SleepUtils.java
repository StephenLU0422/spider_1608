package cn.project.spider_1608.utils;

public class SleepUtils {
	public static void sleep(long millions){
		try {
			Thread.sleep(millions);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
