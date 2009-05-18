/*
 * Copyright 2007-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.config.core.config.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigReader;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.BooleanConversionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * 設定情報をプロパティファイルから読み込むためのクラスです。
 * 
 * @author j5ik2o
 */
public class ConfigPropertiesReader extends AbstractConfigReader {
	private static final Logger LOG =
		Logger.getLogger(ConfigPropertiesReader.class);

	private Properties properties;

	private boolean opened;

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#toMap()
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> result = CollectionsUtil.newHashMap();
		if (properties != null) {
			for (Object key : properties.keySet()) {
				result.put((String) key, properties.get(key));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#load(java.util.Map)
	 */
	public void load(Map<String, Object> configResource) {
		properties = new Properties();
		for (String key : configResource.keySet()) {
			properties.put(key, configResource.get(key));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#open(java.lang.String)
	 */
	public void open(String configName) {
		String configPropertiesName = configName.concat(".properties");
		try {
			properties = ResourceUtil.getProperties(configPropertiesName);
		} catch (ResourceNotFoundRuntimeException e) {
			LOG.warn(String.format(
				"プロパティファイルがみつかりません。(%s)",
				configPropertiesName));
		}
		opened = true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#close()
	 */
	public void close() {
		opened = false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.
	 * Class, java.lang.String)
	 */
	public <T extends Object> T readConfigValue(Class<T> resultClass, String key) {
		return readConfigValue(resultClass, key, null);
	}

	@SuppressWarnings("unchecked")
	private <T> T convertValue(Class<T> type, String value) {
		if (type == String.class) {
			return (T) value;
		} else if (type == int.class
			|| type == short.class
			|| type == long.class
			|| type == float.class
			|| type == double.class
			|| type == byte.class
			|| type == Integer.class
			|| type == BigInteger.class
			|| type == BigDecimal.class
			|| type == Long.class
			|| type == Short.class
			|| type == Float.class
			|| type == Double.class
			|| type == Byte.class) {
			Object parseValue = NumberConversionUtil.convertNumber(type, value);
			return (T) parseValue;
		} else if (type == boolean.class || type == Boolean.class) {
			Boolean parseValue = BooleanConversionUtil.toBoolean(value);
			return (T) parseValue;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.
	 * Class, java.lang.String, java.lang.Object)
	 */
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

	public boolean isOpened() {
		return opened;
	}
}
