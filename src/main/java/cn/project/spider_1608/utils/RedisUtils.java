package cn.project.spider_1608.utils;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	String host ="192.168.43.98";
	int port =6379;
	JedisPool jedisPool = null;
	public static String start_url="start_url";
	public static String key = "spider.todo.url";
	public RedisUtils(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(10);//总的连接
		poolConfig.setMaxTotal(100);//空闲连接
		poolConfig.setMaxWaitMillis(3000);
		poolConfig.setTestOnBorrow(true);
		
		JedisPool resource = new JedisPool(poolConfig, host, port);
	}
	//lrang,查看列表元素
	public List<String> lrange(String key,int start,int end){
		Jedis resource = jedisPool.getResource();
		List<String> list = resource.lrange(key, start, end);
		resource.close();
		return list;
	}
	
	//lpush，列表左弹入
	public void add(String lowKey,String url){
		Jedis resource = jedisPool.getResource();
		resource.lpush(lowKey, url);
		resource.close();
	}
	//rpop,列表右弹出
	public String poll(String key) {
		Jedis resource = jedisPool.getResource();//获取资源
		String result = resource.rpop(key);
		resource.close();
		return result;
	}
	
}
