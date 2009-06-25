package org.seasar.config.core.container;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.config.core.config.ApplicationConfig;
import org.seasar.config.core.dto.ApplicationConfigDto;
import org.seasar.framework.unit.Seasar2;

/**
 * ConfigInjectorクラスのテスト
 *
 * @author j5ik2o
 * @author happy_ryo
 */
@RunWith(Seasar2.class)
public class ConfigInjectorTest {
	private ConfigContainer configContainer;

	private ApplicationConfig applicationConfig;

	private ApplicationConfigDto applicationConfigDto;

	@Test
	public void testInject() {
		configContainer.setConfigName("test");
		configContainer.loadToBeans();
		assertEquals("abcdef", applicationConfig.hoge);
		assertEquals(false, applicationConfig.debug);
                assertEquals("readOnly", applicationConfig.readOnlyTrue);
                assertEquals("readOnlyFalse", applicationConfig.readOnlyFalse);
		assertEquals(Integer.valueOf(100), applicationConfig.xxx);
		assertEquals("abcdef", applicationConfigDto.hoge);
		assertEquals(false, applicationConfigDto.debug);
		assertEquals(Integer.valueOf(100), applicationConfigDto.xxx);
	}
}
