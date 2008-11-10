package org.seasar.config.core.container.impl;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.config.core.container.ConfigContainer;
import org.seasar.config.core.container.ConfigInjector;
import org.seasar.config.core.util.ConfigContainerTraversal;
import org.seasar.config.core.util.ConfigContainerTraversal.ConfigContainerHandler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.log.Logger;

/**
 * コンフィグを管理するコンテナクラスです．
 * 
 * @author junichi
 */
public class ConfigContainerImpl implements ConfigContainer {

	private static Logger log = Logger.getLogger(ConfigContainerImpl.class);

	private boolean initialized;

	private String configName;

	private ConfigWriter configWriter;

	private ConfigReader configReader;

	private ConfigContainer parentConfigContainer;

	private ConfigContainer childConfigContainer;

	private S2Container s2Container;

	private ConfigInjector configInjector;

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.IConfigContainer#dispose()
	 */
	public void dispose() {
		configReader.close();
		configWriter.close();
		if (this.childConfigContainer != null) {
			this.childConfigContainer.dispose();
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#loadToBeans()
	 */
	public void loadToBeans() {
		this.initialize();
		this.configInjector.inject(this, true);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#saveFromBeans()
	 */
	public void saveFromBeans() {
		this.initialize();
		this.configInjector.inject(this, false);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#findAllConfigContainer(java.lang.String)
	 */
	public ConfigContainer findAllConfigContainer(final String configName) {
		this.initialize();
		ConfigContainer result =
			ConfigContainerTraversal.forEachChild(this,
				new ConfigContainerHandler<ConfigContainer>() {

					public ConfigContainer proccess(ConfigContainer container) {
						return configName.equals(container.getConfigName()) ? container
							: null;
					}
				});
		return result;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#findAllConfigValue(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T> T findAllConfigValue(final Class<T> resultClass, final String key) {
		return findAllConfigValue(resultClass, key, null);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#findAllConfigValue(java.lang.Class,
	 *      java.lang.String, T)
	 */
	public <T> T findAllConfigValue(final Class<T> resultClass,
		final String key, final T defaultValue) {
		this.initialize();
		T result =
			ConfigContainerTraversal.forEachParent(this,
				new ConfigContainerHandler<T>() {

					public T proccess(ConfigContainer container) {
						T result =
							container.getConfigValue(resultClass, key,
								defaultValue);
						return result;
					}
				});
		return result;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#getChildConfigContainer()
	 */
	public ConfigContainer getChildConfigContainer() {
		return childConfigContainer;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#getConfigName()
	 */
	public String getConfigName() {
		return configName;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(java.lang.Class,
	 *      java.lang.String, T)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key, T defaultValue) {
		this.initialize();
		T result =
			this.configReader.readConfigValue(resultClass, key, defaultValue);
		return result;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key) {
		return getConfigValue(resultClass, key, null);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(java.lang.String,
	 *      T)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getConfigValue(String key, T defaultValue) {
		return (T) getConfigValue(Object.class, key, defaultValue);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.IConfigContainer#getParentConfigContainer()
	 */
	public ConfigContainer getParentConfigContainer() {
		return parentConfigContainer;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#initialize()
	 */
	public synchronized void initialize() {
		try {
			if (initialized) {
				return;
			}
			configReader.open(configName);
			configWriter.open(configName);
			String env = configReader.readConfigValue(String.class, "env");
			if (env != null) {
				childConfigContainer =
					(ConfigContainer) s2Container
						.getComponent(ConfigContainer.class);
				childConfigContainer.setConfigName(String.format("%s_%s",
					configName, env));
				childConfigContainer.initialize();
				childConfigContainer.setParentConfigContainer(this);
			}
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#putConfigValue(java.lang.String,
	 *      T)
	 */
	public <T> void putConfigValue(String key, T value) {
		this.initialize();
		this.configWriter.writeConfigValue(key, value);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setChildConfigContainer(org.seasar.config.core.container.impl.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		this.childConfigContainer = childConfigContainer;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setConfigName(java.lang.String)
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setConfigReader(org.seasar.config.core.config.ConfigReader)
	 */
	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setConfigWriter(org.seasar.config.core.config.ConfigWriter)
	 */
	public void setConfigWriter(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setParentConfigContainer(org.seasar.config.core.container.impl.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setParentConfigContainer(ConfigContainer parentConfigContainer) {
		this.parentConfigContainer = parentConfigContainer;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setS2Container(org.seasar.framework.container.S2Container)
	 */
	public void setS2Container(S2Container container) {
		s2Container = container;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#sync()
	 */
	public void sync() {
		this.configWriter.flash();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.config.core.container.impl.ConfigContainer#setConfigInjector(org.seasar.config.core.container.ConfigInjector)
	 */
	public void setConfigInjector(ConfigInjector configInjector) {
		this.configInjector = configInjector;
	}
}