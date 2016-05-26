package com.mymock.nutch.nbgov;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

import com.mymock.nutch.BaseFort;

public class NbgovListTest extends BaseFort {


	@Test
	public void commonConf() throws ConfigurationException {
		Configurations configurations = new Configurations();
		PropertiesConfiguration config = configurations.properties("mysite.properties");
		String backColor = config.getString("colors.background");
		assertThat("a's value should be 'b'", backColor, equalTo("#FFFFFF"));
		
		String[] colorList = config.getStringArray("colors.pie");
		assertThat(Arrays.asList(colorList), contains("#FF0000", "#00FF00", "#0000FF"));
	}

}
