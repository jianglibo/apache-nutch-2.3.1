package com.mymock.nutch.nbgov;

import com.mymock.nutch.ConfigurationUtil;

public enum NbgovConfHolder {
	INSTANCE;
	private NbgovHttpCustom singleton;
	
	private NbgovConfHolder() {
		this.singleton = ConfigurationUtil.getConfig("mysite.yml", NbgovHttpCustom.class);
	}
	
	public NbgovHttpCustom get() {
		return singleton;
	}
}
