package com.mymock.nutch.nbgov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;

import com.mymock.nutch.LocalExecutor;

public class NbgovListFetcher {
	
	protected static final Pattern meta = Pattern.compile(".*?<totalrecord>(\\d+)</totalrecord>", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	protected static final Pattern alink = Pattern.compile(".*?<a\\s+[^<]*?href=['\"]{1}([^'\"]+?)['\"]{1}[^<]+?<", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	
//	<a href='http://gtoc.ningbo.gov.cn/art/2016/5/27/art_167_362116.html' class='bt_content_w' title='海曙连续四年拍视频自曝“脏乱差”' target='_blank'>海曙连续四年拍视频自曝“脏乱差”</a> 
	
	public int getTotal(QsFd qsfd, String url) throws ClientProtocolException, IOException {
		Matcher m = meta.matcher(getContent(qsfd, url));
		if (m.lookingAt()) {
			return Integer.valueOf(m.group(1));
		}
		return 0;
	}
	
	
	public List<String> fetch(QsFd qsfd, String url) throws ClientProtocolException, IOException {
		return extractLins(getContent(qsfd, url));
	}
	
	protected List<String> extractLins(String content) {
		Matcher m = NbgovListFetcher.alink.matcher(content);
		List<String> urls = new ArrayList<>();
		while(m.find()) {
			urls.add(m.group(1));
		}
		return urls;
	}
	
	private String getContent(QsFd qsfd, String url) throws ClientProtocolException, IOException {
		NbgovHttpCustom hpc = NbgovConfHolder.INSTANCE.get();
		
		Executor executor = LocalExecutor.INSTANCE.get();
		Request r = Request.Post(url);
		hpc.getHeaders().entrySet().forEach(entry -> {
			r.addHeader(entry.getKey(), entry.getValue());
		});
		
		List<NameValuePair> nvps = (new ArrayList<>(qsfd.getFormDatas().entrySet())).stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
		r.bodyForm(nvps);
		
		String content = executor.execute(r).returnContent().asString();
		return content;
	}
}
