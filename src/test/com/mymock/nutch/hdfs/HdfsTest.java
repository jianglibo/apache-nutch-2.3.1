package com.mymock.nutch.hdfs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Assert;
import org.junit.Test;

import com.mymock.nutch.BaseFort;

public class HdfsTest extends BaseFort {

	@Test
	public void createFolder() throws IOException {

		FileSystem fs = getFs();
		Path homed = fs.getHomeDirectory();
		printme(homed);
		
		printme(fs.exists(homed));
		printme(fs.exists(new Path("/user/root")));
		printme(fs.exists(new Path("/user")));
		
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/"), false);
		while (it.hasNext()) {
			LocatedFileStatus lfstatus = it.next();
			printme(lfstatus.getPath());
		}
	}
	
	@Test
	public void testDir() throws IOException {
		FileSystem fs = getFs();
		Path t1 = new Path("/user/root/t1");
		fs.mkdirs(t1);
		fs.mkdirs(t1);
		fs.mkdirs(t1);
		assertTrue("/usr/root/t1 should created.", fs.exists(t1));
		fs.delete(t1, false);
		assertFalse(fs.exists(t1));
		Path tfile = new Path("/usr/root/t1.txt");
		FSDataOutputStream fdsos =  fs.create(tfile);
		fdsos.write("hello hdfs".getBytes());
		fdsos.flush();
		fdsos.close();
		
		FSDataInputStream dfsis = fs.open(tfile);
		byte[] buf = new byte[4096];
		int readed = 0;
		
		while((readed = dfsis.read(buf)) != -1) {
			printme(new String(Arrays.copyOf(buf, readed)));
			readed = 0;
		}
		if (readed > 0) {
			printme(new String(Arrays.copyOf(buf, readed)));
		}
		dfsis.close();
	}

	@Test
	public void testConfigration() {
		Assert.assertTrue(true);
		 Configuration conf = new Configuration();
		 assertNotNull(conf.get("fs.defaultFS"));
	}

}
