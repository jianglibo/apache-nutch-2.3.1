package com.mymock.nutch;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class BaseFort {
	
	public FileSystem getFs() throws IOException {
		URI uri = URI.create("hdfs://nn.intranet.fh.gov.cn/");
		Path path = new Path(uri);
		Configuration conf = new Configuration();
		
		FileSystem fs = path.getFileSystem(conf);
		return fs;
	}
	
	public void printme(Object o) {
		System.out.println(o);
	}
}
