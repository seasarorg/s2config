/*
 * Copyright 2007-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.config.core.container.impl;

import java.util.Map;

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
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * コンフィグを管理するコンテナクラスです．
 * 
 * @author j5ik2o
 */
public class ConfigContainerImpl implements ConfigContainer {
	@SuppressWarnings("unused")
	private static final Logger LOG =
		Logger.getLogger(ConfigContainerImpl.class);

	private boolean initialized;

	private String configName;

	private ConfigWriter configWriter;

	private ConfigReader configReader;

	private ConfigContainer parentConfigContainer;

	private ConfigContainer childConfigContainer;

	private S2Container s2Container;

	private ConfigInjector configInjector;

	private boolean loadedToBeans;

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.impl.IConfigContainer#dispose()
	 */
	public void dispose() {
		configReader.close();
		configWriter.close();
		if (childConfigContainer != null) {
			childConfigContainer.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.impl.ConfigContainer#loadToBeans()
	 */
	public void loadToBeans() {
		initialize();
		loadedToBeans = configInjector.inject(this, true);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#saveFromBeans()
	 */
	public void saveFromBeans() {
		initialize();
		configInjector.inject(this, false);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#findAllConfigContainer
	 * (java.lang.String)
	 */
	public ConfigContainer findAllConfigContainer(final String configName) {
		initialize();
		ConfigContainer result =
			ConfigContainerTraversal.forEachChild(
				this,
				new ConfigContainerHandler<ConfigContainer>() {
					public ConfigContainer proccess(ConfigContainer container) {
						return configName.equals(container.getConfigName())
							? container
							: null;
					}
				});
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#findAllConfigValue
	 * (java.lang.Class, java.lang.String)
	 */
	public <T> T findAllConfigValue(final Class<T> resultClass, final String key) {
		return findAllConfigValue(resultClass, key, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#findAllConfigValue
	 * (java.lang.Class, java.lang.String, T)
	 */
	public <T> T findAllConfigValue(final Class<T> resultClass,
			final String key, final T defaultValue) {
		initialize();
		T result =
			ConfigContainerTraversal.forEachParent(
				this,
				new ConfigContainerHandler<T>() {
					public T proccess(ConfigContainer container) {
						T result =
							container.getConfigValue(
								resultClass,
								key,
								defaultValue);
						return result;
					}
				});
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#getChildConfigContainer
	 * ()
	 */
	public ConfigContainer getChildConfigContainer() {
		return childConfigContainer;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#getConfigName()
	 */
	public String getConfigName() {
		return configName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(
	 * java.lang.Class, java.lang.String, T)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key, T defaultValue) {
		initialize();
		T result = configReader.readConfigValue(resultClass, key, defaultValue);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(
	 * java.lang.Class, java.lang.String)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key) {
		return getConfigValue(resultClass, key, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#getConfigValue(
	 * java.lang.String, T)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getConfigValue(String key, T defaultValue) {
		return (T) getConfigValue(Object.class, key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.seasar.config.core.container.impl.IConfigContainer#
	 * getParentConfigContainer()
	 */
	public ConfigContainer getParentConfigContainer() {
		return parentConfigContainer;
	}

	/*
	 * (non-Javadoc)
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
				childConfigContainer.setConfigName(String.format(
					"%s_%s",
					configName,
					env));
				childConfigContainer.initialize();
				childConfigContainer.setParentConfigContainer(this);
			}
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#putConfigValue(
	 * java.lang.String, T)
	 */
	public <T> void putConfigValue(String key, T value) {
		initialize();
		configWriter.writeConfigValue(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setChildConfigContainer
	 * (org.seasar.config.core.container.impl.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		this.childConfigContainer = childConfigContainer;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setConfigName(java
	 * .lang.String)
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setConfigReader
	 * (org.seasar.config.core.config.ConfigReader)
	 */
	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setConfigWriter
	 * (org.seasar.config.core.config.ConfigWriter)
	 */
	public void setConfigWriter(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.seasar.config.core.container.impl.ConfigContainer#
	 * setParentConfigContainer
	 * (org.seasar.config.core.container.impl.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setParentConfigContainer(ConfigContainer parentConfigContainer) {
		this.parentConfigContainer = parentConfigContainer;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setS2Container(
	 * org.seasar.framework.container.S2Container)
	 */
	public void setS2Container(S2Container container) {
		s2Container = container;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.impl.ConfigContainer#sync()
	 */
	public void sync() {
		configWriter.flash();
		if (childConfigContainer != null) {
			childConfigContainer.sync();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.config.core.container.impl.ConfigContainer#setConfigInjector
	 * (org.seasar.config.core.container.ConfigInjector)
	 */
	public void setConfigInjector(ConfigInjector configInjector) {
		this.configInjector = configInjector;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#isLoadedToBeans()
	 */
	public boolean isLoadedToBeans() {
		return loadedToBeans;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#loadFromMap(java.lang
	 * .String, java.util.Map)
	 */
	public void loadFromMap(String configName,
			Map<String, Map<String, Object>> resourceMap) {
		Map<String, Object> configResource = resourceMap.get(configName);
		this.configName = configName;
		configReader.load(configResource);
		String env = configReader.readConfigValue(String.class, "env");
		if (env != null) {
			childConfigContainer =
				(ConfigContainer) s2Container
					.getComponent(ConfigContainer.class);
			childConfigContainer.setConfigName(String.format(
				"%s_%s",
				configName,
				env));
			loadFromMap(childConfigContainer.getConfigName(), resourceMap);
			childConfigContainer.setParentConfigContainer(this);
		}
		initialized = true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#getConfigMap()
	 */
	public Map<String, Object> getConfigMap() {
		Map<String, Object> result = CollectionsUtil.newHashMap();
		result.putAll(configReader.toMap());
		Map<String, Object> writerMap = configWriter.toMap();
		for (String key : writerMap.keySet()) {
			result.put(key, writerMap.get(key));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#saveToMap(java.util.Map)
	 */
	public void saveToMap(final Map<String, Map<String, Object>> resourceMap) {
		ConfigContainerTraversal.forEachParent(
			this,
			new ConfigContainerHandler<Void>() {
				public Void proccess(ConfigContainer container) {
					ConfigWriter configCacheWriter =
						(ConfigWriter) s2Container
							.getComponent(ConfigWriter.class);
					configCacheWriter
						.open(container.getConfigName() + "_cache");
					for (String key : container.getConfigMap().keySet()) {
						Object value = container.getConfigMap().get(key);
						configCacheWriter.writeConfigValue(key, value);
					}
					resourceMap.put(
						container.getConfigName(),
						configCacheWriter.toMap());
					configCacheWriter.close();
					configCacheWriter = null;
					return null;
				}
			});
	}
}
