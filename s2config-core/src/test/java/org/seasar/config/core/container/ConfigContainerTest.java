package org.seasar.config.core.container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigContainerTest {

	private ConfigContainer configContainer;

	@Test
	public void testFindAllConfigValue() {
		configContainer.setConfigName("test.properties");
		String result = configContainer.findAllConfigValue("test", null);
		System.out.println(result);
	}
}
