package org.seasar.config.core.config;

import java.util.Map;

public interface ConfigReader {
	public Map<String, Object> toMap();

	public void load(Map<String, Object> configResource);

	public void open(String configName);

	public <T> T readConfigValue(Class<T> resultClass, String key,
		T defaultValue);

	public <T> T readConfigValue(Class<T> resultClass, String key);

	public void close();
}
