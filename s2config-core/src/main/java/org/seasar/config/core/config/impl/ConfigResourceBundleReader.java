package org.seasar.config.core.config.impl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.seasar.config.core.config.AbstractConfigReader;

public class ConfigResourceBundleReader extends AbstractConfigReader {

	private ResourceBundle rb;

	public void open(String configName) {
		rb = ResourceBundle.getBundle(configName);
	}

	public void close() {
		rb = null;
	}

	@SuppressWarnings("unchecked")
	public <T> T readConfigValue(String key, T defaultValue) {
		try {
			Object result = rb.getObject(key);
			return (T) result;
		} catch (MissingResourceException ex) {
			return defaultValue;
		}
	}

}
