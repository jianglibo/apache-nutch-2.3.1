package com.mymock.nutch.nbgov;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymock.nutch.exception.NowayToContinueException;
import com.mymock.nutch.hdfs.FileSystemInstance;
import com.mymock.nutch.hdfs.HdfsUtil;

/**
 * 
 * @author jianglibo@gmail.com
 *
 */

public class NbGovEntryPoint {

	private static Logger LOGGER = LoggerFactory.getLogger(NbGovEntryPoint.class);

	public static void main(String[] args) {
		NbgovConfig nc = NbgovConfig.getInstance();
		writeSeedFile();
		
		Map<String, NbgovCatalogConfig> catMap = new HashMap<>();
		
		if (args.length > 0) {
			String cat = args[0];
			NbgovCatalogConfig ncc = nc.getCatalogsAfterApplyTpl().get(cat);
			if (ncc == null) {
				LOGGER.error("no catalog {} found.", cat);
				throw new NowayToContinueException("no catalog found.", null);
			}
			catMap.put(cat, ncc);
		} else {
			catMap = nc.getCatalogsAfterApplyTpl();
		}
		
		long start = System.currentTimeMillis();
		for (Map.Entry<String, NbgovCatalogConfig> entry : catMap.entrySet()) {
			long cstart = System.currentTimeMillis();
			LOGGER.info("start process " + entry.getKey());
			FetchResultSaverHdfs frs = new FetchResultSaverHdfs(nc, entry.getKey());
			NbgovCatalogFetcher ncf = new NbgovCatalogFetcher(entry.getValue(), frs);
			try {
				ncf.start();
				frs.done();
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage());
			}
			LOGGER.info("{} done. cost {} minuts.", entry.getKey(), (System.currentTimeMillis() - cstart) / 60000);
		}
		LOGGER.info("total done. cost {} minuts.", (System.currentTimeMillis() - start) / 60000);
	}

	private static void writeSeedFile() {
		List<String> seedUrls = NbgovConfig.getInstance().getSeedUrls();
		try {
			Path seedFile = HdfsUtil.mkdirsAndReturnFilePath(NbgovConfig.getInstance().getSeedFolder(), "seed.txt");
			FSDataOutputStream fdsos = FileSystemInstance.get().create(seedFile);
			for (String url : seedUrls) {
				fdsos.write(url.getBytes());
				fdsos.write("\n".getBytes());
			}
			fdsos.flush();
			fdsos.close();
		} catch (IOException e) {
			throw new NowayToContinueException("hdfs communication error.", e);
		}
	}
}
