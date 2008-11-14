package org.seasar.config.core.container;

import java.util.ArrayList;
import java.util.List;

import org.seasar.config.core.autodetector.ConfigClassAutoDetector;
import org.seasar.config.core.config.ConfigValidator;
import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;
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

public class ConfigInjector {

	private static Logger log = Logger.getLogger(ConfigInjector.class);

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
		Traversal.forEachComponent(s2Container,
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

	public void inject(ConfigContainer rootConfigContainer, boolean toBeans) {
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
					ConfigKey configKey =
						propDesc.getField().getAnnotation(ConfigKey.class);
					if (toBeans && propDesc.isWritable()
						&& !configKey.readOnly()) {
						putConfigValueToBeanValue(target, config,
							configContainer, propDesc);
					} else if (propDesc.isReadable()) {
						if (configKey != null) {
							configKeyName = configKey.name();
						}
						Object value = propDesc.getValue(target);
						configContainer.putConfigValue(configKeyName, value);
					}
				}
			}

		}
	}

	private void putConfigValueToBeanValue(Object target, Config config,
		ConfigContainer configContainer, PropertyDesc propDesc) {
		String configKeyName = propDesc.getPropertyName();
		ConfigKey configKey =
			propDesc.getField().getAnnotation(ConfigKey.class);
		if (configKey != null) {
			configKeyName = configKey.name();
		}
		Class<?> clazz = propDesc.getField().getType();
		Object value =
			configContainer.findAllConfigValue(clazz, configKeyName, null);
		if (value != null) {
			log
				.debug(String
					.format(
						"PropertyDesc %s : configName = %s, configKeyName = %s, value = %s",
						propDesc, config.name(), configKeyName, value
							.toString()));
			propDesc.setValue(target, value);
		}
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
