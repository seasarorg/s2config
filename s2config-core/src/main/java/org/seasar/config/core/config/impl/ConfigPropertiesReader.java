package org.seasar.config.core.config.impl;

import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigReader;
import org.seasar.framework.util.ResourceUtil;

public class ConfigPropertiesReader extends AbstractConfigReader {

	private Properties properties;

	public void open(String configName) {
		properties = ResourceUtil.getProperties(configName);
	}

	public void close() {

	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T readConfigValue(String key, T defaultValue) {

		String result = properties.getProperty(key, defaultValue.toString());
		if (result == null) {
			return defaultValue;
		}
		T returnValue = null;
		if (defaultValue.getClass().equals(Integer.class)) {
			Integer parseValue = Integer.parseInt(result);
			returnValue = (T) parseValue;
		}
		if (defaultValue.getClass().equals(Long.class)) {
			Long parseValue = Long.parseLong(result);
			returnValue = (T) parseValue;
		}
		if (defaultValue.getClass().equals(Boolean.class)) {
			Boolean parseValue = Boolean.parseBoolean(result);
			returnValue = (T) parseValue;
		}
		return returnValue;

	}
}
