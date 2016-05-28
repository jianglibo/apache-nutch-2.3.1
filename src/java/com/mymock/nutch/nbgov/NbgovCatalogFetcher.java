package com.mymock.nutch.nbgov;

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

	private NbgovConfig config = NbgovConfigHolder.INSTANCE.get();

	private final NbgovCatalogConfig catalog;

	private final FetchResultSaver saver;

	public NbgovCatalogFetcher(String catalogName, FetchResultSaver saver) {
		this.catalog = config.getCatagories().get(catalogName).init();
		this.saver = saver;
	}

	public void start() throws InterruptedException {
		ListeningExecutorService service = NbgovListeningFutureServiceHolder.INSTANCE.get();
		// we use only one future callback.
		NbgovFetchFutureCallback ffcb = new NbgovFetchFutureCallback(getCatalog(), saver);
		int poolSize = config.getFetchThreads();
		int currentPage = 0;

		while (true) {
			if (ffcb.isDone()) {
				break;
			}
			int poolRemains = poolSize - ffcb.getRunningNumber();
			if (poolRemains > 0) {
				for (int i = 0; i < poolRemains * 2; i++) {
					if (currentPage < catalog.getPageLimit()) {
						ListenableFuture<FetchResult> fetch = service
								.submit(new NbgovOneUrlFetcher(catalog, getCatalog().getPageUrl(currentPage)));
						Futures.addCallback(fetch, ffcb.incrementIn());
						currentPage++;
					} else {
						break;
					}
				}
			}
			Thread.sleep(10);
		}
	}

	protected void doStart() {

	}

	public NbgovCatalogConfig getCatalog() {
		return catalog;
	}
}
