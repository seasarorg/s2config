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
 * {@link ConfigWriter}のためのラッパークラスです。
 * 
 * @author j5ik2o
 */
public class ConfigWriterWrapper extends AbstractConfigWriter {
	private final ConfigWriter configWriter;

	/**
	 * コンストラクタです。
	 * 
	 * @param configWriter
	 *            {@link ConfigWriter}
	 */
	public ConfigWriterWrapper(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#close()
	 */
	public void close() {
		configWriter.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#flash()
	 */
	public void flash() {
		configWriter.flash();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#open(java.lang.String)
	 */
	public void open(String configName) {
		configWriter.open(configName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#toMap()
	 */
	public Map<String, Object> toMap() {
		return configWriter.toMap();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigWriter#writeConfigValue(java.lang
	 * .String, java.lang.Object)
	 */
	public <T> void writeConfigValue(String key, T value) {
		configWriter.writeConfigValue(key, value);
	}
}
