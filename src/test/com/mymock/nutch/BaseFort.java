package com.mymock.nutch;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class BaseFort {
	
	public FileSystem getFs() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.newInstance(conf);
		return fs;
	}
	

	public void printme(Object o) {
		System.out.println(o);
	}
}
