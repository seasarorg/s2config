package org.seasar.config.core.container;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.config.core.config.ApplicationConfig;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigInjectorTest {
	private ConfigContainer configContainer;

	private ApplicationConfig applicationConfig;

	@Test
	public void testInject() {
		configContainer.setConfigName("test");
		configContainer.loadToBeans();
		assertEquals("abc", applicationConfig.hoge);
		assertEquals(true, applicationConfig.debug);
	}
}
