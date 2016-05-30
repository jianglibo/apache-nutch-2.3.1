package com.mymock.nutch.hdfs;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtil {

	public static Path mkdirsAndReturnFilePath(String folder, String filename) throws IOException {
		FileSystem fs = FileSystemInstance.get();

		Path seedFolder = new Path(folder);
		if (!seedFolder.isAbsolute()) {
			seedFolder = new Path(fs.getHomeDirectory(), seedFolder);
		}

		if (!fs.exists(seedFolder)) {
			fs.mkdirs(seedFolder);
		}
		return new Path(seedFolder, filename);
	}

}
