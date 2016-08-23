package cn.project.spider_1608.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HbaseUtils {
	/**
	 * Hbase 表名称
	 */
	public static final String TABLE_NAME = "spider";
	
	/**
	 * 列族1 商品信息
	 */
	public static final String COLUMNFAMILY_1 = "goodsinfo";
	
	/**
	 * 列族1中的列
	 */
	public static final String COLUMNFAMILY_1_DATA_URL = "data_url";
	public static final String COLUMNFAMILY_1_PIC_URL = "pic_url";
	public static final String COLUMNFAMILY_1_TITLE = "title";
	public static final String COLUMNFAMILY_1_PRICE = "price";
	
	/**
	 * 列族2 商品规格
	 */
	public static final String COMLIMNFAMILY_2 = "spec";
	public static final String COMLIMNFAMILY_2_PARAM = "param";
			
	
	HBaseAdmin admin = null;
	Configuration conf = null;
	/**
	 * 构造函数，加载配置
	 */
	public HbaseUtils(){
		conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "192.168.43.98");
		conf.set("hbase.rootdir", "hdfs://192.168.43.98");
		try {
			admin = new HBaseAdmin(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		HbaseUtils hbase = new HbaseUtils();
		//查询所有表名
		hbase.getALLTable();
		
	}
	
	private List<String> getALLTable() throws Exception {
		ArrayList<String> tables = new ArrayList<String>();
		if (admin!=null) {
			HTableDescriptor[] listTables = admin.listTables();
			if (listTables.length>0) {
				for (HTableDescriptor tableDesc : listTables) {
					tables.add(tableDesc.getNameAsString());
					System.out.println(tableDesc.getNameAsString());
				}
				
			}
			
		}
		return tables;
		
	}
	

}
