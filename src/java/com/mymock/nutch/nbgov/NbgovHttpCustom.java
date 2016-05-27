package com.mymock.nutch.nbgov;

import java.util.Map;

public class NbgovHttpCustom {
	
	private Map<String, String> headers;
	
	private Map<String, QsFd> urls; 
	

	public Map<String, QsFd> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, QsFd> urls) {
		this.urls = urls;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
