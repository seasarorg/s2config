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

public class ConfigPropertiesWriter extends AbstractConfigWriter {

	private Properties properties;

	private String configFilePath;

	private boolean changed = false;

	public Map<String, Object> toMap() {
		Map<String, Object> result = CollectionsUtil.newHashMap();
		for (Object key : properties.keySet()) {
			result.put((String) key, properties.get(key));
		}
		return result;
	}

	public void open(String configName) {
		try {
			String realConfigName = configName.concat(".properties");
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

	public void close() {
		flash();
		properties = null;
	}

	public <T extends Object> void writeConfigValue(String key, T value) {
		properties.setProperty(key, value.toString());
		changed = true;
	}

}
