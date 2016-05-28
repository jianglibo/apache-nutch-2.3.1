package com.mymock.nutch.nbgov;

import com.mymock.nutch.ConfigurationUtil;

public enum NbgovConfigHolder {
	INSTANCE;
	private NbgovConfig singleton;
	
	private NbgovConfigHolder() {
		this.singleton = ConfigurationUtil.getConfig("mysite.yml", NbgovConfig.class);
	}
	
	public NbgovConfig get() {
		return singleton;
	}
}
