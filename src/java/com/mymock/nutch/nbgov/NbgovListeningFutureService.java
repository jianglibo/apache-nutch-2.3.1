package com.mymock.nutch.nbgov;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class NbgovListeningFutureService {
	
	public static  ListeningExecutorService getInstance() {
		return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NbgovConfig.getInstance().getFetchThreads()));
	}
}
