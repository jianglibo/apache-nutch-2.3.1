package com.mymock.nutch.nbgov;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.mymock.nutch.BaseFort;

public class NbGovFetchTest extends BaseFort {


	@Test
	public void testGetUrls() {
		QsFd qsfd = NbgovConfHolder.INSTANCE.get().getUrls().get("jrgz");
		List<String> urlsToFetch = qsfd.getFetchUrls(100, 3);
		assertThat(urlsToFetch.size(), equalTo(3));
	}

	@Test
	public void fetch() throws ClientProtocolException, IOException {
		QsFd qsfd = NbgovConfHolder.INSTANCE.get().getUrls().get("jrgz");
		String urlToFetch = qsfd.getFetchUrls(0, 3).get(0);
		
		NbgovListFetcher nf = new NbgovListFetcher();
		List<String> urls = nf.fetch(qsfd, urlToFetch);
		
		assertThat("should return 46 items", urls.size(), equalTo(46));
	}
}
