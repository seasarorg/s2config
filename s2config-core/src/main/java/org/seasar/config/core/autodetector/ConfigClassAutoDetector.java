package org.seasar.config.core.autodetector;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.seasar.config.core.config.ConfigValidator;
import org.seasar.framework.autodetector.impl.AbstractClassAutoDetector;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassLoaderUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class ConfigClassAutoDetector extends AbstractClassAutoDetector {

	private static final String PACKAGE_NAME_CONFIG = "config";

	protected final List<Class<? extends Annotation>> annotations = CollectionsUtil
			.newArrayList();

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

	@InitMethod
	public void init() {
		if (this.namingConvention != null) {
			final String taskPackageName = PACKAGE_NAME_CONFIG;
			for (final String rootPackageName : this.namingConvention
					.getRootPackageNames()) {
				final String packageName = ClassUtil.concatName(
						rootPackageName, taskPackageName);
				this.addTargetPackageName(packageName);
			}
		}
	}

	public void addAnnotation(final Class<? extends Annotation> annotation) {
		// this.annotations.add(annotation);
	}

	@SuppressWarnings("unchecked")
	public void detect(final ClassHandler handler) {
		for (int i = 0; i < this.getTargetPackageNameSize(); i++) {
			final String packageName = this.getTargetPackageName(i);
			for (final Iterator<URL> it = ClassLoaderUtil
					.getResources(packageName.replace('.', '/')); it.hasNext();) {
				this.detect(handler, packageName, it.next());
			}
		}
	}

	protected void detect(final ClassHandler handler,
			final String taskPackageName, final URL url) {
		final Strategy strategy = this.getStrategy(url.getProtocol());
		strategy.detect(taskPackageName, url, new ClassHandler() {
			public void processClass(final String packageName,
					final String shortClassName) {
				if (packageName.startsWith(taskPackageName)
						&& ConfigClassAutoDetector.this.isConfig(packageName,
								shortClassName)) {
					handler.processClass(packageName, shortClassName);
				}
			}
		});
	}

	protected boolean isConfig(final String packageName,
			final String shortClassName) {
		final String name = ClassUtil.concatName(packageName, shortClassName);
		final Class<?> clazz = this.getClass(name);
		return configValidator.isValid(clazz);
	}

	protected Class<?> getClass(final String className) {
		if (this.classLoader != null) {
			return ReflectionUtil.forName(className, this.classLoader);
		}
		return ReflectionUtil.forNameNoException(className);
	}

	public void setConfigValidator(ConfigValidator configValidator) {
		this.configValidator = configValidator;
	}
}
