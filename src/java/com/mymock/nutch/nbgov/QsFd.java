package com.mymock.nutch.nbgov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mymock.nutch.HttpUrlUtils;

public class QsFd {

	private Map<String, String> queryStrings;

	private Map<String, String> formDatas;

	private String url;

	public List<String> getFetchUrls(int total, int pages) {
		int perpage = Integer.valueOf(queryStrings.get("perpage"));
		int startrecord = 1;
		int endrecord = 1;

		List<String> urlsToFetch = new ArrayList<>();

		while (endrecord < total) {
			endrecord = startrecord + perpage * pages - 1;
			urlsToFetch.add(HttpUrlUtils.appendQueryString(getUrl(), "startrecord", String.valueOf(startrecord),
					"endrecord", String.valueOf(endrecord), "perpage", String.valueOf(perpage)));
			startrecord = endrecord + 1;
		}
		
		return urlsToFetch;
	}

	public Map<String, String> getQueryStrings() {
		return queryStrings;
	}

	public void setQueryStrings(Map<String, String> queryStrings) {
		this.queryStrings = queryStrings;
	}

	public Map<String, String> getFormDatas() {
		return formDatas;
	}

	public void setFormDatas(Map<String, String> formDatas) {
		this.formDatas = formDatas;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
