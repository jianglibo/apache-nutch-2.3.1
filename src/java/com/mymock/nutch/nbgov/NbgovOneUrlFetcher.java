package com.mymock.nutch.nbgov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;

import com.mymock.nutch.LocalExecutor;

public class NbgovOneUrlFetcher implements Callable<FetchResult> {
	
	protected static final Pattern meta = Pattern.compile(".*?<totalrecord>(\\d+)</totalrecord>", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	protected static final Pattern alink = Pattern.compile(".*?<a\\s+[^<]*?href=['\"]{1}([^'\"]+?)['\"]{1}[^<]+?<", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	private NbgovCatalogConfig catalog;
	
	private String baseUrl;
	
	public NbgovOneUrlFetcher(NbgovCatalogConfig catalog, String baseUrl) {
		this.catalog = catalog;
		this.baseUrl = baseUrl;
	}
	
	private int getTotal(String content) throws ClientProtocolException, IOException {
		Matcher m = meta.matcher(getContent());
		if (m.lookingAt()) {
			return Integer.valueOf(m.group(1));
		}
		return 0;
	}
	
	
	public FetchResult fetch() throws ClientProtocolException, IOException {
		String content = getContent();
		return new FetchResult(extractLins(content), getTotal(content));
	}
	
	protected List<String> extractLins(String content) {
		Matcher m = NbgovOneUrlFetcher.alink.matcher(content);
		List<String> urls = new ArrayList<>();
		while(m.find()) {
			urls.add(m.group(1));
		}
		return urls;
	}
	
	private String getContent() throws ClientProtocolException, IOException {
		NbgovConfig hpc = NbgovConfigHolder.INSTANCE.get();
		
		Executor executor = LocalExecutor.INSTANCE.get();
		Request r = Request.Post(baseUrl);
		hpc.getHeaders().entrySet().forEach(entry -> {
			r.addHeader(entry.getKey(), entry.getValue());
		});
		
		List<NameValuePair> nvps = (new ArrayList<>(catalog.getFormDatas().entrySet())).stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
		r.bodyForm(nvps);
		
		String content = executor.execute(r).returnContent().asString();
		return content;
	}


	@Override
	public FetchResult call() throws Exception {
		return fetch();
	}
}
