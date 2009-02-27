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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigWriter;
import org.seasar.config.core.exception.FileNotFoundRuntimeException;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * 設定情報をプロパティファイルに書き込むためのクラスです。
 * 
 * @author j5ik2o
 */
public class ConfigPropertiesWriter extends AbstractConfigWriter {
	private static final String PROPERTIES = ".properties";

	private Properties properties;

	private String configFilePath;

	private boolean changed = false;

	/*
	 * (非 Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#toMap()
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
	 * (非 Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#open(java.lang.String)
	 */
	public void open(String configName) {
		try {
			String realConfigName = configName.concat(PROPERTIES);
			File file = ResourceUtil.getResourceAsFile(realConfigName, null);
			configFilePath = file.getAbsolutePath();
			properties = ResourceUtil.getProperties(realConfigName);
		} catch (ResourceNotFoundRuntimeException e) {
			properties = new Properties();
		}
		changed = false;
	}

	private void closeOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
		}
	}

	public synchronized void flash() {
		if (!changed) {
			return;
		}
		if (configFilePath != null) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(configFilePath);
				properties.store(fos, null);
			} catch (FileNotFoundException e) {
				throw new FileNotFoundRuntimeException(e);
			} catch (IOException e) {
				throw new IORuntimeException(e);
			} finally {
				closeOutputStream(fos);
			}
		}
		changed = false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.config.core.config.ConfigWriter#close()
	 */
	public void close() {
		flash();
		properties = null;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.config.core.config.ConfigWriter#writeConfigValue(java.lang
	 * .String, java.lang.Object)
	 */
	public <T extends Object> void writeConfigValue(String key, T value) {
		if (value != null) {
			properties.setProperty(key, value.toString());
			changed = true;
		}
	}
}
