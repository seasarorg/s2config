package org.seasar.config.core.container;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.Disposable;

public class ConfigContainer implements Disposable {

	private String configName;
	private ConfigWriter configWriter;
	private ConfigReader configReader;
	private ConfigContainer childContainer;
	private S2Container s2Container;

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigName() {
		return configName;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
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
			childContainer = (ConfigContainer) s2Container
					.getComponent(ConfigContainer.class);
			String[] configNameSplit = configName.split("\\.");
			childContainer.setConfigName(String.format("%s_%s.properties",
					configNameSplit[0], env));
			childContainer.initialize();
		}
	}

	public void dispose() {
		configReader.close();
		if (this.childContainer != null) {
			this.childContainer.dispose();
		}
	}

}
