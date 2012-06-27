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
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

/**
 * {@link ConfigContainer}のラッパークラスです．
 * 
 * @author j5ik2o
 */
public class ConfigContainerWrapper implements ConfigContainer {
	private final ConfigContainer configContainer;

	/**
	 * コンストラクタです．
	 * 
	 * @param configContainer
	 *            内部に格納する{@link configContainer}
	 */
	public ConfigContainerWrapper(ConfigContainer configContainer) {
		this.configContainer = configContainer;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#findAllConfigContainer
	 * (java.lang.String)
	 */
	public ConfigContainer findAllConfigContainer(String configName) {
		return configContainer.findAllConfigContainer(configName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#findAllConfigValue(java
	 * .lang.Class, java.lang.String)
	 */
	public <T> T findAllConfigValue(Class<T> resultClass, String key) {
		return configContainer.findAllConfigValue(resultClass, key);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#findAllConfigValue(java
	 * .lang.Class, java.lang.String, java.lang.Object)
	 */
	public <T> T findAllConfigValue(Class<T> resultClass, String key,
			T defaultValue) {
		return configContainer.findAllConfigValue(
			resultClass,
			key,
			defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#getChildConfigContainer
	 * ()
	 */
	public ConfigContainer getChildConfigContainer() {
		return configContainer.getChildConfigContainer();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#getConfigName()
	 */
	public String getConfigName() {
		return configContainer.getConfigName();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#getConfigValue(java.
	 * lang.Class, java.lang.String, java.lang.Object)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key, T defaultValue) {
		return configContainer.getConfigValue(resultClass, key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#getConfigValue(java.
	 * lang.Class, java.lang.String)
	 */
	public <T> T getConfigValue(Class<T> resultClass, String key) {
		return configContainer.getConfigValue(resultClass, key);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#getConfigValue(java.
	 * lang.String, java.lang.Object)
	 */
	public <T> T getConfigValue(String key, T defaultValue) {
		return configContainer.getConfigValue(key, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#getParentConfigContainer
	 * ()
	 */
	public ConfigContainer getParentConfigContainer() {
		return configContainer.getParentConfigContainer();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#initialize()
	 */
	public void initialize() {
		configContainer.initialize();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#loadToBeans()
	 */
	public void loadToBeans() {
		configContainer.loadToBeans();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#putConfigValue(java.
	 * lang.String, java.lang.Object)
	 */
	public <T> void putConfigValue(String key, T value) {
		configContainer.putConfigValue(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#saveFromBeans()
	 */
	public void saveFromBeans() {
		configContainer.saveFromBeans();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setChildConfigContainer
	 * (org.seasar.config.core.container.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		configContainer.setChildConfigContainer(childConfigContainer);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setConfigInjector(org
	 * .seasar.config.core.container.ConfigInjector)
	 */
	public void setConfigInjector(ConfigInjector configInjector) {
		configContainer.setConfigInjector(configInjector);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setConfigName(java.lang
	 * .String)
	 */
	public void setConfigName(String configName) {
		configContainer.setConfigName(configName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setConfigReader(org.
	 * seasar.config.core.config.ConfigReader)
	 */
	public void setConfigReader(ConfigReader configReader) {
		configContainer.setConfigReader(configReader);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setConfigWriter(org.
	 * seasar.config.core.config.ConfigWriter)
	 */
	public void setConfigWriter(ConfigWriter configWriter) {
		configContainer.setConfigWriter(configWriter);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setParentConfigContainer
	 * (org.seasar.config.core.container.ConfigContainer)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setParentConfigContainer(ConfigContainer parentConfigContainer) {
		configContainer.setParentConfigContainer(parentConfigContainer);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#setS2Container(org.seasar
	 * .framework.container.S2Container)
	 */
	public void setS2Container(S2Container container) {
		configContainer.setS2Container(container);
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#sync()
	 */
	public void sync() {
		configContainer.sync();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.framework.util.Disposable#dispose()
	 */
	public void dispose() {
		configContainer.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#isLoadedToBeans()
	 */
	public boolean isLoadedToBeans() {
		return configContainer.isLoadedToBeans();
	}

	/*
	 * (non-Javadoc)
	 * @see org.seasar.config.core.container.ConfigContainer#getConfigMap()
	 */
	public Map<String, Object> getConfigMap() {
		return configContainer.getConfigMap();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#loadFromMap(java.lang
	 * .String, java.util.Map)
	 */
	public void loadFromMap(String configName,
			Map<String, Map<String, Object>> resourceMap) {
		configContainer.loadFromMap(configName, resourceMap);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.core.container.ConfigContainer#saveToMap(java.util.Map)
	 */
	public void saveToMap(Map<String, Map<String, Object>> resourceMap) {
		configContainer.saveToMap(resourceMap);
	}
}
