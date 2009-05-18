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
package org.seasar.config.core.config;

import java.util.Map;

/**
 * {@link ConfigReader}のためのラッパークラスです。
 * 
 * @author j5ik2o
 */
public class ConfigReaderWrapper extends AbstractConfigReader {
	private final ConfigReader configReader;

	/**
	 * コンストラクタです。
	 * 
	 * @param configReader
	 *            {@link ConfigReader}
	 */
	public ConfigReaderWrapper(ConfigReader configReader) {
		this.configReader = configReader;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#close()
	 */
	public void close() {
		configReader.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#load(java.util.Map)
	 */
	public void load(Map<String, Object> configResource) {
		configReader.load(configResource);
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#open(java.lang.String)
	 */
	public void open(String configName) {
		configReader.open(configName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.
	 * Class, java.lang.String, java.lang.Object)
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key,
			T defaultValue) {
		return configReader.readConfigValue(resultClass, key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.
	 * Class, java.lang.String)
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key) {
		return configReader.readConfigValue(resultClass, key);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.config.core.config.ConfigReader#toMap()
	 */
	public Map<String, Object> toMap() {
		return configReader.toMap();
	}

	public boolean isOpened() {
		return configReader.isOpened();
	}
}
