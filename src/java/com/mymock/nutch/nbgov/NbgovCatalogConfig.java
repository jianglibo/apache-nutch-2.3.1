package com.mymock.nutch.nbgov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mymock.nutch.HttpUrlUtils;

public class NbgovCatalogConfig {

	private Map<String, String> queryStrings;

	private Map<String, String> formDatas;
	
	private int pageLimit;

	private String baseUrl;
	
	private int perpage;

	public NbgovCatalogConfig init() {
		if (getPageLimit() == 0) {
			setPageLimit(Integer.MAX_VALUE);
		}
		setPerpage(Integer.valueOf(queryStrings.get("perpage")));
		return this;
	}
	/**
	 * 
	 * @param page zero based.
	 * @return
	 */
	public String getPageUrl(int page) {
		int perpage = Integer.valueOf(queryStrings.get("perpage"));
		int startrecord = page * perpage + 1;
		int endrecord = (page + 1) * perpage;
		return createAurl(startrecord, endrecord, perpage);
	}

	public List<String> getFetchUrls(int total, int pages) {
		int perpage = Integer.valueOf(queryStrings.get("perpage"));
		
		if (total == 0) {
			total = perpage; 
		}

		int startrecord = 1;
		int endrecord = 1;

		List<String> urlsToFetch = new ArrayList<>();

		while (endrecord < total) {
			endrecord = startrecord + perpage * pages - 1;
			if (endrecord > total) {
				endrecord = total;
			}
			urlsToFetch.add(createAurl(startrecord, endrecord, perpage));
			startrecord = endrecord + 1;
		}
		return urlsToFetch;
	}
	
	private String createAurl(int startrecord, int endrecord, int perpage) {
		return HttpUrlUtils.appendQueryString(getBaseUrl(), "startrecord", String.valueOf(startrecord),
				"endrecord", String.valueOf(endrecord), "perpage", String.valueOf(perpage));
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

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the pageLimit
	 */
	public int getPageLimit() {
		return pageLimit;
	}

	/**
	 * @param pageLimit the pageLimit to set
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	/**
	 * @param perpage the perpage to set
	 */
	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}
	
	public int getPerpage() {
		return perpage;
	}

}
