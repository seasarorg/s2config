package org.seasar.config.example.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.config.core.container.ConfigContainer;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class Log4JConfigTest {

	private Log4JConfig log4JConfig;

	@Test
	public void test() {
		assertNotNull(log4JConfig);
		System.out.println(log4JConfig.category);
	}

	@Test
	public void test2() {
		ConfigContainer config = SingletonS2Container
				.getComponent(ConfigContainer.class);
		System.out.println(config.getConfigValue(String.class,
				"log4j.category.org.seasar"));
	}

}
