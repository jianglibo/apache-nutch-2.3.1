package com.mymock.nutch.nbgov;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jianglibo@gmail.com
 *
 */

public class NbGovEntryPoint {
	
	private static Logger LOGGER = LoggerFactory.getLogger(NbGovEntryPoint.class);

	public static void main(String[] args) {
		NbgovConfig nc = NbgovConfig.getInstance();
		
		for(Map.Entry<String, NbgovCatalogConfig> entry : nc.getCatagories().entrySet()) {
			LOGGER.info("start process " + entry.getKey());
			FetchResultSaverHdfs frs = new FetchResultSaverHdfs(nc, entry.getKey());
			NbgovCatalogFetcher ncf = new NbgovCatalogFetcher(entry.getValue(), frs);
			try {
				ncf.start();
				frs.done();
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage());
			}
		}
		
		LOGGER.info("total done.");
	}
}
