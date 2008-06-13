package org.seasar.config.core.config.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigPropertiesWriterTest {

	private ConfigPropertiesWriter configPropertiesWriter;

	@Test
	public void testWriteConfigValue() {
		configPropertiesWriter.open("test.properties");
		configPropertiesWriter.writeConfigValue("xxx", 100);
		configPropertiesWriter.close();

	}

}
