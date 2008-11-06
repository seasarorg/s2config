package org.seasar.config.core.config.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigPropertiesReaderTest {

	private ConfigPropertiesReader configPropertiesReader;

	@Test
	public void testReadConfigValue() {
		configPropertiesReader.open("test");
		int ret =
			configPropertiesReader.readConfigValue(Integer.class, "xxx", 0);
		Assert.assertEquals(100, ret);
	}

}
