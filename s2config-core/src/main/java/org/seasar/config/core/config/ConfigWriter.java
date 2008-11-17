package org.seasar.config.core.config;

import java.util.Map;

public interface ConfigWriter {
	Map<String, Object> toMap();

	public void open(String configName);

	public <T> void writeConfigValue(String key, T value);

	public void flash();

	public void close();

}
