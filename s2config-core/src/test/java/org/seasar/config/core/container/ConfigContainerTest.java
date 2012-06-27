package org.seasar.config.core.container;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigContainerTest {
	private ConfigContainer configContainer;

	@Test
	public void testSync() {
		configContainer.setConfigName("test");
		configContainer.initialize();
		configContainer.getChildConfigContainer().putConfigValue(
			"syncTest",
			"xxx");
		configContainer.saveFromBeans();
		configContainer.sync();
	}

	@Test
	public void testFindAllConfigValue() {
		configContainer.setConfigName("test");
		String result =
			configContainer.findAllConfigValue(String.class, "testSync");
		System.out.println(result);
		assertNotNull(result);
		configContainer.dispose();
	}

	@Test
	public void testFindAllChildConfigContainer() {
		configContainer.setConfigName("test");
		ConfigContainer childConfigContainer =
			configContainer.findAllConfigContainer("test_custom");
		assertNotNull(childConfigContainer);
		configContainer.dispose();
	}
}
