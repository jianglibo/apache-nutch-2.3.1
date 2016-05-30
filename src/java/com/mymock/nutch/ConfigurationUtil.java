package com.mymock.nutch;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.mymock.nutch.exception.NowayToContinueException;

public class ConfigurationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtil.class);
	
	private static String toggleResorceName(String resourceName) {
		if (resourceName.startsWith("/")) {
			return resourceName.substring(1);
		} else {
			return "/" + resourceName;
		}
	}

	public static <T> T getConfig(String resourceName, Class<T> c) {
//		Properties p = System.getProperties();
//		Enumeration<Object> keys = p.keys();
//		while (keys.hasMoreElements()) {
//			String key = (String) keys.nextElement();
//			String value = (String) p.get(key);
//			System.out.println(key + ": " + value);
//		}
//		System.out.println(ConfigurationUtil.class.getClassLoader());
		Yaml yaml = new Yaml();
		InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
		if (is == null) {
			is = ClassLoader.getSystemResourceAsStream(toggleResorceName(resourceName));
		}
		if (is == null) {
			LOGGER.info("{} not found in classpath.", resourceName);
			String[] cmd = System.getProperty("sun.java.command").split("\\s+");
			String jarFile = null;
			for (String s : cmd) {
				if (s.contains("apache-nutch-2.3.1.job")) {
					jarFile = s;
				}
			}
			try {
				if (jarFile != null) {
					URL url = new URL("file", null, jarFile);
					URLClassLoader cl = URLClassLoader.newInstance(new URL[] { url }); 
					is = cl.getResourceAsStream(resourceName);
					if (is == null) {
						is = cl.getResourceAsStream(toggleResorceName(resourceName));
					}
					if (is == null) {
						LOGGER.error("cannot find {}", resourceName);
						throw new NowayToContinueException("resource not found.", null);
					}
				} else {
					throw new NowayToContinueException("resource not found.", null);
				}
			} catch (MalformedURLException e) {
				throw new NowayToContinueException("resource not found.", e);
			}
		}

		return yaml.loadAs(is, c);
	}
}
