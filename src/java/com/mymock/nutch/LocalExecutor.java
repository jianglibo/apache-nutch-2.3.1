package com.mymock.nutch;

import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.impl.client.BasicCookieStore;

public enum LocalExecutor {
	INSTANCE;
	private Executor singleton;
	
	private LocalExecutor() {
		CookieStore cookieStore = new BasicCookieStore();
		singleton = Executor.newInstance();
		singleton.cookieStore(cookieStore);
	}
	
	public Executor get() {
		return singleton;
	}
}
