package org.seasar.config.core.config.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.seasar.config.core.config.AbstractConfigWriter;
import org.seasar.config.core.exception.FileNotFoundRuntimeException;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceUtil;

public class ConfigPropertiesWriter extends AbstractConfigWriter {

	private Properties properties;
	private String configFilePath;

	public void open(String configName) {
		File file = ResourceUtil.getResourceAsFile(configName, null);
		configFilePath = file.getAbsolutePath();
		properties = ResourceUtil.getProperties(configName);
	}

	public void close() {
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

		properties = null;
	}

	public <T extends Object> void writeConfigValue(String key, T value) {
		properties.put(key, value.toString());

	}

}
