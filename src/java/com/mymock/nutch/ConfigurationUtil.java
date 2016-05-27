package com.mymock.nutch;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class ConfigurationUtil {
	
	public static <T>  T getConfig(String resourceName, Class<T> c) {
		Yaml yaml = new Yaml();
		InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
		return yaml.loadAs(is, c);
	}

}
