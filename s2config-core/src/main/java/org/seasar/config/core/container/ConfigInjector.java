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
package org.seasar.config.core.container;

import java.util.ArrayList;
import java.util.List;

import org.seasar.config.core.autodetector.ConfigClassAutoDetector;
import org.seasar.config.core.config.ConfigValidator;
import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigIgnore;
import org.seasar.config.core.config.annotation.ConfigKey;
import org.seasar.config.core.util.ConfigContainerTraversal;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.SmartDeployUtil;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

/**
 * @author j5ik2o
 */
public class ConfigInjector {
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ConfigInjector.class);

	private ConfigClassAutoDetector configClassAutoDetector;

	private ConfigValidator configValidator;

	private final List<ComponentDef> targetComponentDefList =
		new ArrayList<ComponentDef>();

	private S2Container s2Container;

	private void register() {
		registerSmartDepolyComponent(s2Container.getRoot());
		registerS2Component(s2Container.getRoot());
	}

	private void registerS2Component(S2Container s2Container) {
		Traversal.forEachComponent(
			s2Container,
			new Traversal.ComponentDefHandler() {
				public Object processComponent(ComponentDef componentDef) {
					Class<?> clazz = componentDef.getComponentClass();
					if (clazz != null) {
						if (configValidator.isValid(clazz)) {
							if (!targetComponentDefList.contains(componentDef)) {
								targetComponentDefList.add(componentDef);
							}
						}
					}
					return null;
				}
			});
	}

	private void registerSmartDepolyComponent(final S2Container s2Container) {
		if (SmartDeployUtil.isSmartdeployMode(s2Container)) {
			configClassAutoDetector.detect(new ClassTraversal.ClassHandler() {
				public void processClass(String packageName,
						String shortClassName) {
					String name =
						ClassUtil.concatName(packageName, shortClassName);
					Class<?> clazz = ReflectionUtil.forNameNoException(name);
					if (clazz != null) {
						try {
							ComponentDef componentDef =
								s2Container.getComponentDef(clazz);
							if (!targetComponentDefList.contains(componentDef)) {
								targetComponentDefList.add(componentDef);
							}
						} catch (ComponentNotFoundRuntimeException e) {
							;
						}
					}
				}
			});
		}
	}

	/**
	 * 設定情報をインジェクトします。
	 * 
	 * @param rootConfigContainer
	 *            {@link ConfigContainer}
	 * @param toBeans
	 *            設定情報からJavaBeansにインジェクトする場合はtrue,
	 *            JavaBeanから設定情報をインジェクトする場合false,
	 * @return インジェクトできた場合はtrue, できなかった場合はfalse
	 */
	public boolean inject(ConfigContainer rootConfigContainer, boolean toBeans) {
		boolean result = false;
		register();
		for (final ComponentDef componentDef : targetComponentDefList) {
			Object target = componentDef.getComponent();
			Class<?> clazz = componentDef.getComponentClass();
			Config config = clazz.getAnnotation(Config.class);
			ConfigContainer configContainer =
				rootConfigContainer.findAllConfigContainer(config.name());
			if (configContainer != null) {
				BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
				for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
					PropertyDesc propDesc = beanDesc.getPropertyDesc(i);
					String configKeyName = propDesc.getPropertyName();
					ConfigIgnore configIgnore =
						propDesc.getField().getAnnotation(ConfigIgnore.class);
					if (configIgnore != null) {
						continue;
					}
					ConfigKey configKey =
						propDesc.getField().getAnnotation(ConfigKey.class);
					boolean readOnly = false;
					if (configKey != null) {
						configKeyName = configKey.name();
						readOnly = configKey.readOnly();
					}
					if (toBeans && propDesc.isWritable()) {
						Object value =
							configContainer.findAllConfigValue(propDesc
								.getField()
								.getType(), configKeyName, null);
						if (value != null) {
							propDesc.setValue(target, value);
						}
						result = true;
					} else if (toBeans == false && propDesc.isReadable()) {
						Object value = propDesc.getValue(target);
						final String targetConfigKeyName = configKeyName;
						// 書き込む先のコンテナを選ぶ
						ConfigContainer targetContainer =
							ConfigContainerTraversal
								.forEachParent(
									configContainer,
									new ConfigContainerTraversal.ConfigContainerHandler<ConfigContainer>() {
										public ConfigContainer proccess(
												ConfigContainer container) {
											if (container
												.getConfigMap()
												.containsKey(
													targetConfigKeyName)) {
												return container;
											}
											return null;
										}
									});
						if (readOnly == false) {
							targetContainer
								.putConfigValue(configKeyName, value);
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * @return the configClassAutoDetector
	 */
	public ConfigClassAutoDetector getConfigClassAutoDetector() {
		return configClassAutoDetector;
	}

	/**
	 * @param configClassAutoDetector
	 *            the configClassAutoDetector to set
	 */
	public void setConfigClassAutoDetector(
			ConfigClassAutoDetector configClassAutoDetector) {
		this.configClassAutoDetector = configClassAutoDetector;
	}

	/**
	 * @param container
	 *            the s2Container to set
	 */
	public void setS2Container(S2Container container) {
		s2Container = container;
	}

	/**
	 * @param configValidator
	 *            the configValidator to set
	 */
	public void setConfigValidator(ConfigValidator configValidator) {
		this.configValidator = configValidator;
	}
}
