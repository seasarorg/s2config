package org.seasar.config.core.config;


public interface ConfigWriter {

	public void open(String configName);

	public <T> void writeConfigValue(String key, T value);

	public void close();

}
