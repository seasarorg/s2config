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
 * @author j5ik2o
 */
public class ConfigContainerWrapper implements ConfigContainer {
	private final ConfigContainer configContainer;

	public ConfigContainerWrapper(ConfigContainer configContainer) {
		this.configContainer = configContainer;
	}

	public ConfigContainer findAllConfigContainer(String configName) {
		return configContainer.findAllConfigContainer(configName);
	}

	public <T> T findAllConfigValue(Class<T> resultClass, String key) {
		return configContainer.findAllConfigValue(resultClass, key);
	}

	public <T> T findAllConfigValue(Class<T> resultClass, String key,
			T defaultValue) {
		return configContainer.findAllConfigValue(
			resultClass,
			key,
			defaultValue);
	}

	public ConfigContainer getChildConfigContainer() {
		return configContainer.getChildConfigContainer();
	}

	public String getConfigName() {
		return configContainer.getConfigName();
	}

	public <T> T getConfigValue(Class<T> resultClass, String key, T defaultValue) {
		return configContainer.getConfigValue(resultClass, key, defaultValue);
	}

	public <T> T getConfigValue(Class<T> resultClass, String key) {
		return configContainer.getConfigValue(resultClass, key);
	}

	public <T> T getConfigValue(String key, T defaultValue) {
		return configContainer.getConfigValue(key, defaultValue);
	}

	public ConfigContainer getParentConfigContainer() {
		return configContainer.getParentConfigContainer();
	}

	public void initialize() {
		configContainer.initialize();
	}

	public void loadToBeans() {
		configContainer.loadToBeans();
	}

	public <T> void putConfigValue(String key, T value) {
		configContainer.putConfigValue(key, value);
	}

	public void saveFromBeans() {
		configContainer.saveFromBeans();
	}

	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		configContainer.setChildConfigContainer(childConfigContainer);
	}

	public void setConfigInjector(ConfigInjector configInjector) {
		configContainer.setConfigInjector(configInjector);
	}

	public void setConfigName(String configName) {
		configContainer.setConfigName(configName);
	}

	public void setConfigReader(ConfigReader configReader) {
		configContainer.setConfigReader(configReader);
	}

	public void setConfigWriter(ConfigWriter configWriter) {
		configContainer.setConfigWriter(configWriter);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setParentConfigContainer(ConfigContainer parentConfigContainer) {
		configContainer.setParentConfigContainer(parentConfigContainer);
	}

	public void setS2Container(S2Container container) {
		configContainer.setS2Container(container);
	}

	public void sync() {
		configContainer.sync();
	}

	public void dispose() {
		configContainer.dispose();
	}

	public boolean isLoadedToBeans() {
		return configContainer.isLoadedToBeans();
	}

	public Map<String, Object> getConfigMap() {
		return configContainer.getConfigMap();
	}

	public void loadFromMap(String configName,
			Map<String, Map<String, Object>> resourceMap) {
		configContainer.loadFromMap(configName, resourceMap);
	}

	public void saveToMap(Map<String, Map<String, Object>> resourceMap) {
		configContainer.saveToMap(resourceMap);
	}
}
