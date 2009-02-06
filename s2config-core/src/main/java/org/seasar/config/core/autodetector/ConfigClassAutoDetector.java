package org.seasar.config.core.autodetector;

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
import org.seasar.framework.util.tiger.ReflectionUtil;

/**
 * Configクラスを自動的に認識するクラスです。
 * 
 * @author kato
 */
public class ConfigClassAutoDetector extends AbstractClassAutoDetector {
	private static final String PACKAGE_NAME_CONFIG = "config";

	private static final String PACKAGE_NAME_DTO = "dto";

	// protected final List<Class<? extends Annotation>> annotations =
	// CollectionsUtil.newArrayList();

	protected NamingConvention namingConvention;

	protected ClassLoader classLoader;

	private ConfigValidator configValidator;

	/**
	 * コンストラクタです。
	 */
	public ConfigClassAutoDetector() {
		// this.annotations.add(Task.class);
	}

	/**
	 * {@link NamingConvention}を設定します。
	 * 
	 * @param namingConvention
	 *            {@link NamingConvention}
	 */
	@Binding(bindingType = BindingType.MAY)
	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	/**
	 * {@link ClassLoader}を設定します。
	 * 
	 * @param classLoader
	 *            {@link ClassLoader}
	 */
	@Binding(bindingType = BindingType.MAY)
	public void setClassLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * 認識するサブパッケージ名を追加します。
	 * 
	 * @param packageSubName
	 *            サブパッケージ名
	 */
	private void addTargetSubPackageName(String subPackageName) {
		for (final String rootPackageName : namingConvention
			.getRootPackageNames()) {
			final String packageName =
				ClassUtil.concatName(rootPackageName, subPackageName);
			addTargetPackageName(packageName);
		}
	}

	/**
	 * コンポーネントの初期化処理です。
	 */
	@InitMethod
	public void init() {
		if (namingConvention != null) {
			addTargetSubPackageName(PACKAGE_NAME_CONFIG);
			addTargetSubPackageName(PACKAGE_NAME_DTO);
		}
	}

	// public void addAnnotation(final Class<? extends Annotation> annotation) {
	// // this.annotations.add(annotation);
	// }

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.framework.autodetector.ClassAutoDetector#detect(org.seasar.framework.util.ClassTraversal.ClassHandler)
	 */
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

	/**
	 * Configクラスかどうかを返します。
	 * 
	 * @param packageName
	 *            パッケージ名
	 * @param shortClassName
	 *            ショートクラス名
	 * @return Configクラスであるならtrue, そうでないならfalse。
	 */
	protected boolean isConfig(final String packageName,
		final String shortClassName) {
		final String name = ClassUtil.concatName(packageName, shortClassName);
		final Class<?> clazz = this.getClass(name);
		if (clazz != null) {
			return configValidator.isValid(clazz);
		}
		return false;
	}

	/**
	 * クラスを返します。
	 * 
	 * @param className
	 *            クラス名(FQCN)
	 * @return クラス
	 */
	protected Class<?> getClass(final String className) {
		if (classLoader != null) {
			return ReflectionUtil.forName(className, classLoader);
		}
		return ReflectionUtil.forNameNoException(className);
	}

	/**
	 * {@link ConfigValidator}を設定します。
	 * 
	 * @param configValidator
	 *            {@link ConfigValidator}
	 */
	public void setConfigValidator(ConfigValidator configValidator) {
		this.configValidator = configValidator;
	}
}
