package org.apache.nutch.crawl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsightCodeUtil {
	
	  private static final boolean WINDOWS = System.getProperty("os.name")
		      .startsWith("Windows");

	
	  public static final Logger LOG = LoggerFactory
		      .getLogger(InsightCodeUtil.class);
	  
	public static Path logDir =  new Path("yarnlog");
	
	public static String printClassPath(Configuration conf) {
		return printClassPath(conf, "conf", conf.getClass().getClassLoader());
	}
	public static String printClassPath(Configuration conf,String prefix, ClassLoader sysClassLoader) {
		if (WINDOWS) {
			return "";
		}
		
		try {
			URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
			List<String> lines = new ArrayList<>();
			FileSystem fs = FileSystem.get(conf);
//			RemoteIterator<LocatedFileStatus> ril = fs.listFiles(logDir, true);
			
//			while(ril.hasNext()) {
//				fs.delete(ril.next().getPath(),true);
//			}
			
			Path  p= new Path(logDir,prefix + "Classpath" + new SimpleDateFormat("HHmmss").format(Date.from(Instant.now())));
			FSDataOutputStream fd0 = fs.create(p);

			
			for(int i=0; i< urls.length; i++)
			{
				String s = urls[i].getFile();
				fd0.writeBytes(s + "\n");
				
				if (s.endsWith("/classes/")) {
					java.nio.file.Path classesp = Paths.get(s);
					String sss = Files.walk(classesp).map(pp -> pp.toAbsolutePath().normalize().toString()).collect(Collectors.joining("\n"));
					printTofile(conf, "classes", sss);
				}
			}
			fd0.close();
			return String.join(",", lines);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void printTofile(Configuration conf,String prefix, String s) {
		if (WINDOWS) {
			return;
		}
		
		try {
			Path  p= new Path(logDir, prefix + new SimpleDateFormat("HHmmss").format(Date.from(Instant.now())));
			FileSystem fs = FileSystem.get(conf);
			FSDataOutputStream fd0 = fs.create(p);

			
			fd0.writeBytes(s + "\n");
			
			fd0.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void printCurrentFolderFiles(Configuration conf) {
		try {
			StringBuilder sb = new StringBuilder();
			for(File f: Paths.get(".").toFile().listFiles()) {
				sb.append(f.getAbsolutePath()).append("\n");
			}
			printTofile(conf, "currentFolderFiles", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
