package com.mymock.nutch.hbase;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.gora.query.Query;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Nutch;
import org.apache.nutch.storage.ProtocolStatus;
import org.apache.nutch.storage.StorageUtils;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.TableUtil;
import org.junit.Test;

public class WebpagePlumb {

	private DataStore<String, WebPage> dataStore; 
	
	private Configuration conf() {
		Configuration conf = new Configuration();
		conf.addResource("conf/hbase-site.xml");
//		conf.addResource("conf/gora-hbase-mapping.xml");
		conf.set("storage.data.store.class", "org.apache.gora.hbase.store.HBaseStore");
		conf.set(Nutch.CRAWL_ID_KEY, "fhgov");
//		conf.addResource("conf/gora.properties");
		return conf;
	}
	

//	rg.apache.nutch.protocol.ProtocolStatus
//	public static final int	ACCESS_DENIED	17
//	public static final int	BLOCKED	23
//	public static final int	EXCEPTION	16
//	public static final int	FAILED	2
//	public static final int	GONE	11
//	public static final int	MOVED	12
//	public static final int	NOTFETCHING	20
//	public static final int	NOTFOUND	14
//	public static final int	NOTMODIFIED	21
//	public static final int	PROTO_NOT_FOUND	10
//	public static final int	REDIR_EXCEEDED	19
//	public static final int	RETRY	15
//	public static final int	ROBOTS_DENIED	18
//	public static final int	SUCCESS	1
//	public static final int	TEMP_MOVED	13
//	public static final int	WOULDBLOCK	22
	
	@Test
	public void tQuery() throws Exception {
//		dataStore = DataStoreFactory.getDataStore(String.class, WebPage.class,conf());
		dataStore = StorageUtils.createWebStore(conf(), String.class, WebPage.class);
//		WebPage wp = dataStore.get("http://");
		 Query<String, WebPage> query = dataStore.newQuery();
//		 query.setStartKey("");
		query.setLimit(1); 
		 Result<String, WebPage> result = query.execute();
		 result.next();
		 assertThat(result.getKey(), equalTo(TableUtil.reverseUrl("http://www.fh.gov.cn/")));
		 WebPage wp = result.get();
		 assertThat(wp.getProtocolStatus().getCode(), equalTo(13));
		 
		 wp = dataStore.get(TableUtil.reverseUrl("http://www.fh.gov.cn/"));
		 String c = new String(wp.getContent().array());
		 System.out.println(c);
	}
	
//	public static void main(String[] args) throws Exception {
//		new WebpagePlumb().fetchAll();
//	}
}
