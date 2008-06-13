package org.seasar.config.core.container;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.config.core.util.ConfigContainerTraversal;
import org.seasar.config.core.util.ConfigContainerTraversal.ConfigContainerHandler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.Disposable;

public class ConfigContainer implements Disposable {

	private boolean initialized;
	private String configName;
	private ConfigWriter configWriter;
	private ConfigReader configReader;
	private ConfigContainer childConfigContainer;
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

	private synchronized void initialize() {
		if (initialized) {
			return;
		}
		configReader.open(configName);
		String env = configReader.readConfigValue("env", null);
		if (env != null) {
			childConfigContainer = (ConfigContainer) s2Container
					.getComponent(ConfigContainer.class);
			String[] configNameSplit = configName.split("\\.");
			childConfigContainer.setConfigName(String.format(
					"%s_%s.properties", configNameSplit[0], env));
			childConfigContainer.initialize();
		}
		initialized = true;
	}

	public <T> void putConfigValue(String key, T value) {
		this.initialize();
		this.configWriter.writeConfigValue(key, value);
	}

	public <T> T getConfigValue(String key, T defaultValue) {
		this.initialize();
		T result = this.configReader.readConfigValue(key, defaultValue);
		return result;
	}

	public <T> T findAllConfigValue(final String key, final T defaultValue) {
		this.initialize();
		T result = ConfigContainerTraversal.forEach(this,
				new ConfigContainerHandler<T>() {
					public T proccess(ConfigContainer container) {
						T result = container.getConfigValue(key, defaultValue);
						return result;
					}
				});
		return result;
	}

	public void dispose() {
		configReader.close();
		if (this.childConfigContainer != null) {
			this.childConfigContainer.dispose();
		}
	}

	public ConfigContainer getChildConfigContainer() {
		return childConfigContainer;
	}

	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		this.childConfigContainer = childConfigContainer;
	}

}
