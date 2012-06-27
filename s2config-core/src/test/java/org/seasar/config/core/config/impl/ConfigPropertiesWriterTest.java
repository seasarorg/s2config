package org.seasar.config.core.config.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigPropertiesWriterTest {
	private ConfigPropertiesWriter configPropertiesWriter;

	private ConfigPropertiesReader configPropertiesReader;

	public void afterClass() {
	}

	@Test
	public void testWriteConfigValue() {
		try {
			configPropertiesWriter.open("test");
			configPropertiesWriter.writeConfigValue("xxx", 101);
		} finally {
			if (configPropertiesWriter.isOpened()) {
				configPropertiesWriter.close();
			}
		}
		try {
			configPropertiesReader.open("test");
			Integer xxx =
				configPropertiesReader.readConfigValue(Integer.class, "xxx");
			assertNotNull(xxx);
			assertEquals(Integer.valueOf(101), xxx);
			configPropertiesWriter.open("test");
			configPropertiesWriter.writeConfigValue("xxx", 100);
		} finally {
			configPropertiesReader.close();
			configPropertiesWriter.close();
		}
	}
}
