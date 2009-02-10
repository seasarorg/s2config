package org.seasar.config.core.config;

import java.util.Map;

/**
 * {@link ConfigWriter}のためのラッパークラスです。
 * 
 * @author j5ik2o
 */
public class ConfigWriterWrapper extends AbstractConfigWriter {
	private final ConfigWriter configWriter;

	/**
	 * コンストラクタです。
	 * 
	 * @param configWriter
	 *            {@link ConfigWriter}
	 */
	public ConfigWriterWrapper(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigWriter#close()
	 */
	public void close() {
		configWriter.close();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigWriter#flash()
	 */
	public void flash() {
		configWriter.flash();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigWriter#open(java.lang.String)
	 */
	public void open(String configName) {
		configWriter.open(configName);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigWriter#toMap()
	 */
	public Map<String, Object> toMap() {
		return configWriter.toMap();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.config.ConfigWriter#writeConfigValue(java.lang.String,
	 *      java.lang.Object)
	 */
	public <T> void writeConfigValue(String key, T value) {
		configWriter.writeConfigValue(key, value);
	}
}
