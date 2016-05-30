package com.mymock.nutch.nbgov;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.junit.Test;

import com.mymock.nutch.BaseFort;

public class NbgovCatalogConfigTest extends BaseFort {
	
	@Test
	public void merge() {
		NbgovConfig nc = NbgovConfig.getInstance();
		Map<String, NbgovCatalogConfig> catagories = nc.getCatalogsAfterApplyTpl();
		NbgovCatalogConfig jrgz = catagories.get("rd_jrgz");
		
		assertThat("pageLimit should set", jrgz.getPageLimit(), equalTo(0));
		assertThat("baseUrl should set", jrgz.getBaseUrl(), equalTo("http://gtoc.ningbo.gov.cn/module/web/jpage/dataproxy.jsp"));
		
		assertThat("queryString should set", jrgz.getQueryStrings().get("startrecord"), equalTo("1"));
		assertThat("queryString should set", jrgz.getQueryStrings().get("endrecord"), equalTo("0"));
		assertThat("queryString should set", jrgz.getQueryStrings().get("perpage"), equalTo("15"));
		
		
		assertThat("formDatas col should set", jrgz.getFormDatas().get("col"), equalTo("1"));
		assertThat("formDatas appid should set", jrgz.getFormDatas().get("appid"), equalTo("1"));
		assertThat("formDatas webid should set", jrgz.getFormDatas().get("webid"), equalTo("3"));
		assertThat("formDatas path should set", jrgz.getFormDatas().get("path"), equalTo("/"));
		assertThat("formDatas column should set", jrgz.getFormDatas().get("columnid"), equalTo("158"));
		assertThat("formDatas sourceContentType should set", jrgz.getFormDatas().get("sourceContentType"), equalTo("1"));
		
		assertThat("formDatas unitid should set", jrgz.getFormDatas().get("unitid"), equalTo("4116"));
		assertThat("formDatas webname should set", jrgz.getFormDatas().get("webname"), equalTo("中国·宁波+公民站"));
		assertThat("formDatas permissiontype should set", jrgz.getFormDatas().get("permissiontype"), equalTo("0"));
		
		jrgz = catagories.get("rd_bmts_gg");
		assertThat("formDatas unitid should set", jrgz.getFormDatas().get("unitid"), equalTo("4278"));
		assertThat("formDatas column should set", jrgz.getFormDatas().get("columnid"), equalTo("160"));		
	}

}
