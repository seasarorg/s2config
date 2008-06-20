package org.seasar.config.example.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class Log4JConfigTest {

	private Log4JConfig log4JConfig;

	@Test
	public void test() {
		assertNotNull(log4JConfig);
	}

}
