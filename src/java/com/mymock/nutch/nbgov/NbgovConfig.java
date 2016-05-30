package com.mymock.nutch.nbgov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.mymock.nutch.ConfigurationUtil;

public class NbgovConfig {
	
	private static final String CONF_FILE = "mysite.yml"; 
	
	private List<String> seedUrls;
	
	private String seedFolder;
	
	private int fetchThreads;
	
	private int batchNumber;
	
	private NbgovCatalogConfig cattpl;
	
	private Map<String, String> headers;
	
	private Map<String, NbgovCatalogConfig> catalogs; 
	
	// for tcl script use.
	
	private String crawlID;
	private int numberOfRounds;
	private List<String> preRunClasses;
	
	
	public Map<String, NbgovCatalogConfig> getCatalogsAfterApplyTpl() {
		Map<String, NbgovCatalogConfig> afters = new HashMap<>();
		for(Map.Entry<String, NbgovCatalogConfig> entry: getCatalogs().entrySet()) {
			afters.put(entry.getKey(), mergeConfig(entry.getValue()));
		}
		return afters;
	}
	

	private NbgovCatalogConfig mergeConfig(NbgovCatalogConfig value) {
		if (Strings.isNullOrEmpty(value.getBaseUrl())) {
			value.setBaseUrl(getCattpl().getBaseUrl());
		}
		
		if (value.getQueryStrings() == null) {
			value.setQueryStrings(getCattpl().getQueryStrings());
		} else {
			Map<String, String> qs = value.getQueryStrings();
			for(Map.Entry<String, String> entry : getCattpl().getQueryStrings().entrySet()) {
				if (Strings.isNullOrEmpty(qs.get(entry.getKey()))) {
					qs.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		if (value.getFormDatas() == null) {
			value.setFormDatas(getCattpl().getFormDatas());
		} else {
			Map<String, String> fd = value.getFormDatas();
			for(Map.Entry<String, String> entry : getCattpl().getFormDatas().entrySet()) {
				if (Strings.isNullOrEmpty(fd.get(entry.getKey()))) {
					fd.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return value;
	}


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

	public Map<String, NbgovCatalogConfig> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Map<String, NbgovCatalogConfig> catagory) {
		this.catalogs = catagory;
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


	public NbgovCatalogConfig getCattpl() {
		return cattpl;
	}

	public void setCattpl(NbgovCatalogConfig cattpl) {
		this.cattpl = cattpl;
	}
	
	


	public String getCrawlID() {
		return crawlID;
	}


	public void setCrawlID(String crawlID) {
		this.crawlID = crawlID;
	}


	public int getNumberOfRounds() {
		return numberOfRounds;
	}


	public void setNumberOfRounds(int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}


	public List<String> getPreRunClasses() {
		return preRunClasses;
	}


	public void setPreRunClasses(List<String> preRunClasses) {
		this.preRunClasses = preRunClasses;
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
