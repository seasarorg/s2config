package org.seasar.config.core.config.impl;

import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigReader;
import org.seasar.framework.util.ResourceUtil;

/**
 * 
 * @author junichi
 * 
 */
public class ConfigPropertiesReader extends AbstractConfigReader {

	private Properties properties;

	public void open(String configName) {
		properties = ResourceUtil.getProperties(configName
				.concat(".properties"));
	}

	public void close() {

	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T readConfigValue(String key, T defaultValue) {
		String result = properties.getProperty(key,
				defaultValue != null ? defaultValue.toString() : null);
		if (result == null) {
			return defaultValue;
		}
		try {
			Integer parseValue = Integer.parseInt(result);
			return (T) parseValue;
		} catch (NumberFormatException e) {
			;
		}
		try {
			Long parseValue = Long.parseLong(result);
			return (T) parseValue;
		} catch (NumberFormatException e) {
			;
		}
		String lowercase = result.toLowerCase();
		Boolean parseValue = null;
		if (lowercase.equals("true")) {
			parseValue = Boolean.TRUE;
			return (T) parseValue;
		} else if (lowercase.equals("false")) {
			parseValue = Boolean.FALSE;
			return (T) parseValue;
		}
		return (T) result;

	}
}
