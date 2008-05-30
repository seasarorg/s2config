package org.seasar.config.core.manager;

import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceConfigManager {

	private HashMap<String, Object> defaultValues = new HashMap<String, Object>();
	private String resourceFileName = "userConfig";

	public ResourceConfigManager() {
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public void addDefaultValue(String key, Object value) {
		defaultValues.put(key, value);
	}

	public boolean getConfigBoolean(String key, Boolean defaultValue) {
		addDefaultValue(key, defaultValue.toString());
		return getConfigBoolean(key);
	}

	public boolean getConfigBoolean(String key) {
		String value = getConfigString(key);
		boolean ret = Boolean.parseBoolean(value);
		return ret;
	}

	public int getConfigInt(String key, Integer defaultValue) {
		addDefaultValue(key, defaultValue.toString());
		return getConfigInt(key);
	}

	public int getConfigInt(String key) {
		String value = getConfigString(key);
		int ret = Integer.parseInt(value);
		return ret;
	}

	public String getConfigString(String key, String defaultValue) {
		addDefaultValue(key, defaultValue);
		return getConfigString(key);
	}

	public String getConfigString(String key) {
		String value = null;
		try {
			ResourceBundle rb = getResourceBundle();
			value = rb.getString(key);
		} catch (MissingResourceException ex) {
			value = (String) defaultValues.get(key);
		}
		return value;
	}

	private ResourceBundle getResourceBundle() {
		ResourceBundle rb = ResourceBundle.getBundle(resourceFileName);
		return rb;
	}

}
