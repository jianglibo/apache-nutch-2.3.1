package com.mymock.nutch.tastegora;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.gora.hbase.store.HBaseStore;
import org.apache.gora.query.Query;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.api.model.request.DbFilter;
import org.apache.nutch.storage.StorageUtils;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.nutch.util.TableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DspContent {

	private static Logger LOGGER = LoggerFactory.getLogger(DspContent.class);

	protected DataStore<String, WebPage> store;

	@SuppressWarnings("unchecked")
	public DspContent() {
		Configuration c = new Configuration(NutchConfiguration.create());
		try {
			store = DataStoreFactory.createDataStore(HBaseStore.class, String.class, WebPage.class, c, "nbgov_webpage");
		} catch (Exception e) {
			throw new IllegalStateException("Cannot create webstore!", e);
		}
	}

	public static void main(String[] args) throws Exception {
		DspContent dc = new DspContent();
		DbFilter df = new DbFilter();
		Query<String, WebPage> q = dc.store.newQuery();

		String key = "http://gtob.ningbo.gov.cn/art/2016/6/1/art_249_371742.html";
		key = TableUtil.reverseUrl(key);
		q.setLimit(1);
		q.setStartKey(key);

		Result<String, WebPage> result = q.execute();
		while (result.next()) {
			result.getKey();
			WebPage wp = result.get();
			LOGGER.info("\n fetch time: {},\n batchId: {},\n fetch interval: {},\n",
					Instant.ofEpochMilli(wp.getFetchTime()).toString(), //
					wp.getBatchId(), //
					wp.getFetchInterval()//
			);
		}
	}

	public Result<String, WebPage> runQuery(DbFilter filter) {
		String startKey = filter.getStartKey();
		String endKey = filter.getEndKey();

		if (!filter.isKeysReversed()) {
			startKey = reverseKey(filter.getStartKey());
			endKey = reverseKey(filter.getEndKey());
		}

		Query<String, WebPage> query = store.newQuery();
		query.setFields(prepareFields(filter.getFields()));
		if (startKey != null) {
			query.setStartKey(startKey);
			if (endKey != null) {
				query.setEndKey(endKey);
			}
		}
		return store.execute(query);

	}

	private String reverseKey(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}

		try {
			return TableUtil.reverseUrl(key);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Wrong url format!", e);
		}
	}

	private String[] prepareFields(Set<String> fields) {
		if (CollectionUtils.isEmpty(fields)) {
			return null;
		}
		fields.remove("url");
		return fields.toArray(new String[fields.size()]);
	}

}
