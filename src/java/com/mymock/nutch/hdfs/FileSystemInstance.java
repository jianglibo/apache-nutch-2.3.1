package com.mymock.nutch.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class FileSystemInstance {
	
	public static FileSystem get() {
		return FileSystemInstanceHolder.INSTANCE.get();
	}

	private static enum FileSystemInstanceHolder {
		
		INSTANCE;
		private FileSystem singleton;
		
		private FileSystemInstanceHolder() {
			Configuration conf = new Configuration();
			try {
				this.singleton = FileSystem.newInstance(conf);
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("local hadoop config failed.");
			}
		}
		
		public FileSystem get() {
			return singleton;
		}
	}
}
