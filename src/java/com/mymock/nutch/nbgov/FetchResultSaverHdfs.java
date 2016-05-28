package com.mymock.nutch.nbgov;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymock.nutch.hdfs.FileSystemInstance;

/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class FetchResultSaverHdfs implements FetchResultSaver {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FetchResultSaverHdfs.class);
	
	private FSDataOutputStream fdsos;
	
	/**
	 * 
	 */
	public FetchResultSaverHdfs(NbgovConfig config, String filename) {
		FileSystem fs = FileSystemInstance.get();
		
		Path seedFolder = new Path(config.getSeedFolder());
		if (!seedFolder.isAbsolute()) {
			seedFolder = new Path(fs.getHomeDirectory(), seedFolder);
		}
		
		Path seedFile = new Path(seedFolder, filename);
		try {
			if (!fs.exists(seedFolder)) {
				fs.mkdirs(seedFolder);
			}
			fdsos = fs.create(seedFile);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new IllegalArgumentException("hdfs connection failed.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.mymock.nutch.nbgov.FetchResultSaver#save(com.mymock.nutch.nbgov.FetchResult)
	 */
	@Override
	public void save(FetchResult fr) {
		fr.getExtractedUrls().forEach(url -> {
			try {
				fdsos.write(url.getBytes());
				fdsos.write("\n".getBytes());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				LOGGER.error(url + "decoded failed.");
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.mymock.nutch.nbgov.FetchResultSaver#done()
	 */
	@Override
	public void done() {
		try {
			fdsos.flush();
			fdsos.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
	}
}
