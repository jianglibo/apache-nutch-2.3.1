package com.mymock.nutch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.mymock.nutch.nbgov.NbgovListeningFutureService;

public class ConCurrentUtilTest extends BaseFort {

	@Test
	public void one() throws InterruptedException, ExecutionException {
		Callable<String> ca = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				printme("hello ca");
				return "hello ca";
			}
		};

		FutureCallback<String> fca = new FutureCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				assertThat(arg0, equalTo("hello ca"));
			}
		};
		
		ConcurrentUtil.submitAndWait(NbgovListeningFutureService.getInstance(), ca);
		
	}

}
