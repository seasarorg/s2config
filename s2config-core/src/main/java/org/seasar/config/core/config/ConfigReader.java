package org.seasar.config.core.config;

public interface ConfigReader {
	public void open(String configName);

	public <T> T readConfigValue(String key, T defaultValue);

	public void close();
}
