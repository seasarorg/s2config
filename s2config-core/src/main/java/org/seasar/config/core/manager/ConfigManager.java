package org.seasar.config.core.manager;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.framework.util.Disposable;

public class ConfigManager implements Disposable {

	private String configName;
	private ConfigWriter configWriter;
	private ConfigReader configReader;

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public void setConfigWriter(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	private void initialize() {
		configReader.open(configName);
		String env = configReader.readConfigValue("env", null);
		if (env != null) {

		}
	}

	public void dispose() {
		configReader.close();
	}

}
