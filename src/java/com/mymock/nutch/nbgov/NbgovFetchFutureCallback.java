package com.mymock.nutch.nbgov;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;

public class NbgovFetchFutureCallback implements FutureCallback<FetchResult> {

	private ReentrantLock lock = new ReentrantLock();

	private static final Logger LOGGER = LoggerFactory.getLogger(NbgovFetchFutureCallback.class);

	private final NbgovCatalogConfig catalog;
	
	private final FetchResultSaver saver;

	private int totalPage;

	private int inNumber;

	private int successNumber;

	private int failNumber;

	private boolean done;

	public NbgovFetchFutureCallback(NbgovCatalogConfig catalog, FetchResultSaver saver) {
		this.saver = saver;
		this.catalog = catalog;
		this.inNumber = 0;
		this.successNumber = 0;
		this.failNumber = 0;
	}

	public NbgovFetchFutureCallback incrementIn() {
		this.inNumber++;
		return this;
	}

	public int getRunningNumber() {
		return inNumber - (successNumber + failNumber);
	}

	public void setTotalPage(int totalNumber) {
		if (totalNumber > 0) {
			this.totalPage = totalNumber / catalog.getPerpage();			
		} else {
			setDone(true);
		}
	}
	
	public void alterDone() {
		if (failNumber + successNumber >= totalPage) {
			setDone(true);
		}
	}

	@Override
	public void onFailure(Throwable arg0) {
		this.failNumber++;
		LOGGER.error(arg0.getMessage());
		alterDone();
	}

	@Override
	public void onSuccess(FetchResult fr) {
		lock.lock();
		try {
			this.successNumber++;
			setTotalPage(fr.getTotalNumber());
			saver.save(fr);
			alterDone();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done
	 *            the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

}
