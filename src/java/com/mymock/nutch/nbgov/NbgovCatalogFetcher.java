package com.mymock.nutch.nbgov;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * 
 * @author jianglibo@gmail.com
 * 
 *         May 27, 2016
 * 
 *         fetch all pages of one catalog.
 */
public class NbgovCatalogFetcher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NbgovCatalogFetcher.class);

	private NbgovConfig config = NbgovConfig.getInstance();

	private final NbgovCatalogConfig catalog;

	private final FetchResultSaver saver;
	
	public NbgovCatalogFetcher(NbgovCatalogConfig catalog, FetchResultSaver saver) {
		this.catalog = catalog.init();
		this.saver = saver;
	}

	public NbgovCatalogFetcher(String catalogName, FetchResultSaver saver) {
		this.catalog = config.getCatalogsAfterApplyTpl().get(catalogName).init();
		this.saver = saver;
	}

	public void start() throws InterruptedException {
		ListeningExecutorService service = NbgovListeningFutureService.getInstance();
		// we use only one future callback.
		NbgovFetchFutureCallback ffcb = new NbgovFetchFutureCallback(getCatalog(), saver);
		int poolSize = config.getFetchThreads();
		int currentPage = 0;
		LOGGER.info("start fetch with {} threads", poolSize);
		while (true) {
			if (ffcb.isDone()) {
				break;
			}
			int poolRemains = poolSize - ffcb.getRunningNumber();
//			int roundStart = currentPage;
			if (poolRemains > 0) {
				for (int i = 0; i < poolRemains * 2; i++) {
					if (currentPage < catalog.getPageLimit()) {
						ListenableFuture<FetchResult> fetch = service
								.submit(new NbgovOneUrlFetcher(catalog, getCatalog().getPageUrl(currentPage)));
						Futures.addCallback(fetch, ffcb.incrementIn());
						currentPage++;
					} else {
						ffcb.setDone(true);
						break;
					}
				}
			}
//			LOGGER.info("submit task from {} to {}", roundStart, currentPage);
			Thread.sleep(100);
		}
		service.shutdown();
		service.awaitTermination(10, TimeUnit.MINUTES);
	}

	public NbgovCatalogConfig getCatalog() {
		return catalog;
	}
}
