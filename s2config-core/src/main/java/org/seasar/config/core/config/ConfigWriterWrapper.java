package org.seasar.config.core.config;

import java.util.Map;

public class ConfigWriterWrapper extends AbstractConfigWriter {
	private final ConfigWriter configWriter;

	public ConfigWriterWrapper(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	public void close() {
		configWriter.close();
	}

	public void flash() {
		configWriter.flash();
	}

	public void open(String configName) {
		configWriter.open(configName);
	}

	public Map<String, Object> toMap() {
		return configWriter.toMap();
	}

	public <T> void writeConfigValue(String key, T value) {
		configWriter.writeConfigValue(key, value);
	}

}
