package org.seasar.config.core.config.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigWriter;
import org.seasar.config.core.exception.FileNotFoundRuntimeException;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.ResourceUtil;

public class ConfigPropertiesWriter extends AbstractConfigWriter {

	private Properties properties;
	private String configFilePath;
	private boolean changed = false;

	public void open(String configName) {
		String realConfigName = configName.concat(".properties");
		File file = ResourceUtil.getResourceAsFile(realConfigName, null);
		configFilePath = file.getAbsolutePath();
		try {
			properties = ResourceUtil.getProperties(realConfigName);
		} catch (ResourceNotFoundRuntimeException e) {
			properties = new Properties();
		}
		changed = false;
	}

	public synchronized void flash() {
		if (!changed) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(configFilePath);
			properties.store(fos, null);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundRuntimeException(e);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		}
		changed = false;
	}

	public void close() {
		this.flash();
		properties = null;
	}

	public <T extends Object> void writeConfigValue(String key, T value) {
		properties.setProperty(key, value.toString());
		changed = true;
	}

}
