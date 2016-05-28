package com.mymock.nutch.nbgov;

/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class FetchResultSaverDummy implements FetchResultSaver {
	
	private long saveCount;

	/* (non-Javadoc)
	 * @see com.mymock.nutch.nbgov.FetchResultSaver#save(com.mymock.nutch.nbgov.FetchResult)
	 */
	@Override
	public void save(FetchResult fr) {
		this.saveCount++;
		
	}

	/* (non-Javadoc)
	 * @see com.mymock.nutch.nbgov.FetchResultSaver#getSaveCount()
	 */
	public long getSaveCount() {
		return saveCount;
	}

	/**
	 * @param saveCount the saveCount to set
	 */
	public void setSaveCount(long saveCount) {
		this.saveCount = saveCount;
	}

}
