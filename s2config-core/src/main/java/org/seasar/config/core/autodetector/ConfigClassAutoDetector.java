package org.seasar.config.core.autodetector;

import java.lang.annotation.Annotation;
import java.util.List;

import org.seasar.config.core.config.ConfigValidator;
import org.seasar.framework.autodetector.impl.AbstractClassAutoDetector;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourcesUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.ResourcesUtil.Resources;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class ConfigClassAutoDetector extends AbstractClassAutoDetector {
	private static final String PACKAGE_NAME_CONFIG = "config";

	private static final String PACKAGE_NAME_DTO = "dto";

	protected final List<Class<? extends Annotation>> annotations =
		CollectionsUtil.newArrayList();

	protected NamingConvention namingConvention;

	protected ClassLoader classLoader;

	private ConfigValidator configValidator;

	public ConfigClassAutoDetector() {
		// this.annotations.add(Task.class);
	}

	@Binding(bindingType = BindingType.MAY)
	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	@Binding(bindingType = BindingType.MAY)
	public void setClassLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	private void addTargetPackageSubName(String packageSubName) {
		for (final String rootPackageName : namingConvention
			.getRootPackageNames()) {
			final String packageName =
				ClassUtil.concatName(rootPackageName, packageSubName);
			addTargetPackageName(packageName);
		}
	}

	@InitMethod
	public void init() {
		if (namingConvention != null) {
			addTargetPackageSubName(PACKAGE_NAME_CONFIG);
			addTargetPackageSubName(PACKAGE_NAME_DTO);
		}
	}

	public void addAnnotation(final Class<? extends Annotation> annotation) {
		// this.annotations.add(annotation);
	}

	@SuppressWarnings("unchecked")
	public void detect(final ClassHandler handler) {
		for (int i = 0; i < getTargetPackageNameSize(); i++) {
			final String packageName = getTargetPackageName(i);
			for (final Resources resources : ResourcesUtil
				.getResourcesTypes(packageName)) {
				try {
					resources.forEach(new ClassHandler() {

						public void processClass(final String packageName,
							final String shortClassName) {
							if (packageName.startsWith(packageName)
								&& ConfigClassAutoDetector.this.isConfig(
									packageName, shortClassName)) {
								handler.processClass(packageName,
									shortClassName);
							}
						}
					});
				} finally {
					resources.close();
				}
			}
		}
	}

	protected boolean isConfig(final String packageName,
		final String shortClassName) {
		final String name = ClassUtil.concatName(packageName, shortClassName);
		final Class<?> clazz = this.getClass(name);
		if (clazz != null) {
			return configValidator.isValid(clazz);
		}
		return false;
	}

	protected Class<?> getClass(final String className) {
		if (classLoader != null) {
			return ReflectionUtil.forName(className, classLoader);
		}
		return ReflectionUtil.forNameNoException(className);
	}

	public void setConfigValidator(ConfigValidator configValidator) {
		this.configValidator = configValidator;
	}
}
