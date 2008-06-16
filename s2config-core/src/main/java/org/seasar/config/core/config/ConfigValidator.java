package org.seasar.config.core.config;

import org.seasar.config.core.config.annotation.Config;

public class ConfigValidator {

	public boolean isValid(Class<?> clazz) {
		Config config = clazz.getAnnotation(Config.class);
		if (config != null) {
			return true;
		}
		return false;
	}

}
