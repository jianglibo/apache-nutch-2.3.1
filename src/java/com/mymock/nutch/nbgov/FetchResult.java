package com.mymock.nutch.nbgov;

import java.util.List;

/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class FetchResult {
	
	private List<String> extractedUrls;
	
	private int totalNumber;
	

	/**
	 * @param extractedUrls
	 * @param totalNumber
	 */
	public FetchResult(List<String> extractedUrls, int totalNumber) {
		super();
		this.extractedUrls = extractedUrls;
		this.totalNumber = totalNumber;
	}

	public List<String> getExtractedUrls() {
		return extractedUrls;
	}

	public void setExtractedUrls(List<String> extractedUrls) {
		this.extractedUrls = extractedUrls;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	
 
}
