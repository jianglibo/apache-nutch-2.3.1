package com.mymock.nutch.nbgov;

import java.util.List;
import java.util.Map;

import com.mymock.nutch.ConfigurationUtil;

public class NbgovConfig {
	
	private static final String CONF_FILE = "mysite.yml"; 
	
	private List<String> seedUrls;
	
	private String seedFolder;
	
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
	
	public static NbgovConfig getInstance() {
		return NbgovConfigHolder.INSTANCE.get();
	}


	public String getSeedFolder() {
		return seedFolder;
	}

	public void setSeedFolder(String seedFolder) {
		this.seedFolder = seedFolder;
	}


	private static enum NbgovConfigHolder {
		INSTANCE;
		private NbgovConfig singleton;
		
		private NbgovConfigHolder() {
			this.singleton = ConfigurationUtil.getConfig(CONF_FILE, NbgovConfig.class);
		}
		
		public NbgovConfig get() {
			return singleton;
		}
	}
	
}
