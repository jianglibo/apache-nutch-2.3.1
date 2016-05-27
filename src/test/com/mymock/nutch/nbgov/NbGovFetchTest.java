package com.mymock.nutch.nbgov;

import java.util.List;
import java.util.Map.Entry;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import com.mymock.nutch.BaseFort;
import com.mymock.nutch.ConfigurationUtil;
import com.mymock.nutch.LocalExecutor;

public class NbGovFetchTest extends BaseFort {

	@Test
	public void fetch() {
		Executor executor = LocalExecutor.INSTANCE.get();
		
		NbgovHttpCustom hpc = ConfigurationUtil.getConfig("mysite.yml", NbgovHttpCustom.class);
		for(Entry<String, QsFd> entry : hpc.getUrls().entrySet()) {
			List<String> urlsToFetch = entry.getValue().getFetchUrls(100, 3);
			urlsToFetch.forEach(url -> {
				printme(url);
			});
//			Request req = Request.Post(entry.getValue().getUrl());
		}
		
	}
}
