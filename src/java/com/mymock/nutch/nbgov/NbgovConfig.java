package com.mymock.nutch.nbgov;

import java.util.List;
import java.util.Map;

public class NbgovConfig {
	
	private List<String> seedUrls;
	
	private int fetchThreads;
	
	private int batchNumber;
	
	private Map<String, String> headers;
	
	private Map<String, NbgovCatalogConfig> catagories; 
	

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public int getFetchThreads() {
		return fetchThreads;
	}

	public void setFetchThreads(int threads) {
		this.fetchThreads = threads;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the seedUrls
	 */
	public List<String> getSeedUrls() {
		return seedUrls;
	}

	/**
	 * @param seedUrls the seedUrls to set
	 */
	public void setSeedUrls(List<String> seedUrls) {
		this.seedUrls = seedUrls;
	}

	public Map<String, NbgovCatalogConfig> getCatagories() {
		return catagories;
	}

	public void setCatagories(Map<String, NbgovCatalogConfig> catagory) {
		this.catagories = catagory;
	}
	
}
