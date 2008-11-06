package org.seasar.config.core.container;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigContainerTest {

	private ConfigContainer configContainer;

	@Test
	public void testFindAllConfigValue() {
		configContainer.setConfigName("test");
		String result =
			configContainer.findAllConfigValue(String.class, "test");
		assertNotNull(result);
		System.out.println(result);
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
