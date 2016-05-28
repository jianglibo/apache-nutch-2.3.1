package com.mymock.nutch;

import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.impl.client.BasicCookieStore;


public class LocalRequestExecutor {
	
	public static Executor getInstance() {
		return LocalRequestExecutorHolder.INSTANCE.get();
	}
	
	private enum LocalRequestExecutorHolder {
		INSTANCE;
		private Executor singleton;
		
		private LocalRequestExecutorHolder() {
			CookieStore cookieStore = new BasicCookieStore();
			singleton = Executor.newInstance();
			singleton.cookieStore(cookieStore);
		}
		
		public Executor get() {
			return singleton;
		}
	}

}
