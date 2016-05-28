package com.mymock.nutch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public class ConcurrentUtil {
	
	public static <T> void submitAndListen(ListeningExecutorService service,Callable<T> ca, FutureCallback<T> fca) throws InterruptedException, ExecutionException {
		ListenableFuture<T> lf = service.submit(ca);
		Futures.addCallback(lf, fca);
	}

	public static <T> T submitAndWait(ListeningExecutorService service,Callable<T> ca) throws InterruptedException, ExecutionException {
		ListenableFuture<T> lf = service.submit(ca);
		return lf.get();
	}
	
	public static <T> List<T> submitManyAndWait(ListeningExecutorService service,Iterable<Callable<T>> cas, FutureCallback<T> fca) throws InterruptedException, ExecutionException {
		List<ListenableFuture<T>> lfs = new ArrayList<>();
		
		for(Callable<T> ca : cas) {
			lfs.add(service.submit(ca));
		}
		return Futures.successfulAsList(lfs).get();
	}

}
