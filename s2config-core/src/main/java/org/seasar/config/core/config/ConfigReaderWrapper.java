package org.seasar.config.core.config;

import java.util.Map;

public class ConfigReaderWrapper extends AbstractConfigReader {
	private final ConfigReader configReader;

	public ConfigReaderWrapper(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void close() {
		configReader.close();
	}

	public void load(Map<String, Object> configResource) {
		configReader.load(configResource);
	}

	public void open(String configName) {
		configReader.open(configName);
	}

	public <T> T readConfigValue(Class<T> resultClass, String key,
		T defaultValue) {
		return configReader.readConfigValue(resultClass, key, defaultValue);
	}

	public <T> T readConfigValue(Class<T> resultClass, String key) {
		return configReader.readConfigValue(resultClass, key);
	}

	public Map<String, Object> toMap() {
		return configReader.toMap();
	}

}
