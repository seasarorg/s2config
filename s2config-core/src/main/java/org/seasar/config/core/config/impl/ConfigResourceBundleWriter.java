package org.seasar.config.core.config.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.record.formula.functions.T;
import org.seasar.config.core.config.AbstractConfigWriter;
import org.seasar.config.core.exception.FileNotFoundRuntimeException;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceUtil;

public class ConfigResourceBundleWriter extends AbstractConfigWriter {

	private Properties properties;
	private String configFilePath;

	public void open(String configName) {
		configFilePath = ResourceUtil.getResourcePath(configName, null);
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

	public void writeConfigValue(String key, T value) {
		properties.put(key, value);

	}

}
