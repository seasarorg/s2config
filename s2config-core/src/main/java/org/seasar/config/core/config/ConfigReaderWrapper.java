package org.seasar.config.core.config;

import java.util.Map;

/**
 * {@link ConfigReader}のためのラッパークラスです。
 * 
 * @author j5ik2o
 */
public class ConfigReaderWrapper extends AbstractConfigReader {
	private final ConfigReader configReader;

	/**
	 * コンストラクタです。
	 * 
	 * @param configReader
	 *            {@link ConfigReader}
	 */
	public ConfigReaderWrapper(ConfigReader configReader) {
		this.configReader = configReader;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#close()
	 */
	public void close() {
		configReader.close();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#load(java.util.Map)
	 */
	public void load(Map<String, Object> configResource) {
		configReader.load(configResource);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#open(java.lang.String)
	 */
	public void open(String configName) {
		configReader.open(configName);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.Class,
	 *      java.lang.String, java.lang.Object)
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key,
			T defaultValue) {
		return configReader.readConfigValue(resultClass, key, defaultValue);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#readConfigValue(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key) {
		return configReader.readConfigValue(resultClass, key);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigReader#toMap()
	 */
	public Map<String, Object> toMap() {
		return configReader.toMap();
	}
}
