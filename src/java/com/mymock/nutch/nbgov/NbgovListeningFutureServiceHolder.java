package com.mymock.nutch.nbgov;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public enum NbgovListeningFutureServiceHolder {
	INSTANCE;
	private ListeningExecutorService singleton;
	
	private NbgovListeningFutureServiceHolder() {
		singleton = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NbgovConfigHolder.INSTANCE.get().getFetchThreads()));
	}
	
	public ListeningExecutorService get() {
		return singleton;
	}
}
