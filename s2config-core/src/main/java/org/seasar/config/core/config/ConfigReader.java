package org.seasar.config.core.config;

public interface ConfigReader {

	public void open(String configName);

	public <T> T readConfigValue(Class<T> resultClass, String key,
		T defaultValue);

	public <T> T readConfigValue(Class<T> resultClass, String key);

	public void close();
}
