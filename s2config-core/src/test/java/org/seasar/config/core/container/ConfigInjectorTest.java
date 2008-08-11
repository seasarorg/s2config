package org.seasar.config.core.container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ConfigInjectorTest {

	private ConfigContainer configContainer;

	@Test
	public void testInject() {
		configContainer.setConfigName("test");
		configContainer.loadToBeans();
	}

}
