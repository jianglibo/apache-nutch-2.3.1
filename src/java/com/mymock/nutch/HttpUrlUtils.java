package com.mymock.nutch;

import java.util.LinkedHashMap;

public class HttpUrlUtils {

	public static String appendQueryString(String base, LinkedHashMap<String, String> qs) {
		if (qs.size() == 0) {
			return base;
		}
		StringBuffer sb = new StringBuffer(base);
		if (!base.endsWith("?")) {
			sb.append("?");
		}
		qs.forEach((k, v) -> {
			sb.append(k).append("=").append(v).append("&");
		});

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String appendQueryString(String base, String... qs) {
		if ((qs.length % 2) != 0) {
			throw new IllegalArgumentException("qs must be pairs", null);
		}
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < qs.length; i += 2) {
			map.put(qs[i], qs[i + 1]);
		}
		return appendQueryString(base, map);
	}

}
