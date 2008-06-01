package org.seasar.config.core.config;

import org.apache.poi.hssf.record.formula.functions.T;

public interface ConfigWriter {

	public void open(String configName);

	public void writeConfigValue(String key, T value);

	public void close();

}
