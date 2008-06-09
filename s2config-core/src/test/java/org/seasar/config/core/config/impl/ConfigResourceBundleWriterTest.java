package org.seasar.config.core.config.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigResourceBundleWriterTest {

	private ConfigPropertiesWriter configResourceBundleWriter;

	@Test
	public void testWriteConfigValue() {
		configResourceBundleWriter.open("test.properties");
		configResourceBundleWriter.writeConfigValue("xxx", 100);
		configResourceBundleWriter.close();

	}

}
