package org.seasar.config.core.config.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigReader;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.BooleanConversionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.ResourceUtil;

/**
 * @author junichi
 */
public class ConfigPropertiesReader extends AbstractConfigReader {

	private static Logger log = Logger.getLogger(ConfigPropertiesReader.class);

	private Properties properties;

	public void open(String configName) {
		try {
			properties =
				ResourceUtil.getProperties(configName.concat(".properties"));
		} catch (ResourceNotFoundRuntimeException e) {
			log.warn("プロパティファイルがみつかりません。", e);
		}
	}

	public void close() {

	}

	public <T extends Object> T readConfigValue(Class<T> resultClass, String key) {
		return readConfigValue(resultClass, key, null);
	}

	@SuppressWarnings("unchecked")
	private <T> T convertValue(Class<T> type, String value) {
		if (type == String.class) {
			return (T) value;
		} else if (type == Integer.class || type == BigInteger.class
			|| type == BigDecimal.class || type == Long.class
			|| type == Short.class || type == Float.class
			|| type == Double.class || type == Byte.class) {
			Object parseValue = NumberConversionUtil.convertNumber(type, value);
			return (T) parseValue;
		} else if (type == Boolean.class) {
			Boolean parseValue = BooleanConversionUtil.toBoolean(value);
			return (T) parseValue;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T readConfigValue(Class<T> type, String key,
		T defaultValue) {
		if (properties == null) {
			return defaultValue;
		}
		String result =
			properties.getProperty(key, defaultValue != null ? defaultValue
				.toString() : null);
		if (result == null) {
			return defaultValue;
		}
		if (type == Object.class) {
			Class<T> targetClass = (Class<T>) defaultValue.getClass();
			return convertValue(targetClass, result);
		}
		return convertValue(type, result);
	}
}
