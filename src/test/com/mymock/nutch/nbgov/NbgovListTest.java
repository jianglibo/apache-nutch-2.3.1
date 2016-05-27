package com.mymock.nutch.nbgov;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.mymock.nutch.BaseFort;
import com.mymock.nutch.ConfigurationUtil;

public class NbgovListTest extends BaseFort {

	@Test
	public void headers() {
		NbgovHttpCustom hpc = ConfigurationUtil.getConfig("mysite.yml", NbgovHttpCustom.class);
		assertThat(hpc.getHeaders().get("Content-type"), equalTo("application/x-www-form-urlencoded; charset=UTF-8"));
		
		assertThat(hpc.getUrls().get("jrgz").getFormDatas().get("webname"), equalTo("中国·宁波+公民站"));

	}
	

}
