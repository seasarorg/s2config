package org.seasar.config.core.container;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.config.core.util.ConfigContainerTraversal;
import org.seasar.config.core.util.ConfigContainerTraversal.ConfigContainerHandler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.Disposable;

/**
 * コンフィグを管理するコンテナクラスです．
 * 
 * @author junichi
 * 
 */
public class ConfigContainer implements Disposable {

	private boolean initialized;
	private String configName;
	private ConfigWriter configWriter;
	private ConfigReader configReader;
	private ConfigContainer parentConfigContainer;
	private ConfigContainer childConfigContainer;
	private S2Container s2Container;
	private ConfigInjector configInjector;

	/**
	 * コンフィグコンテナを破棄します．
	 * <p>
	 * コンフィグコンテナを破棄します．子供のコンテナがある場合も破棄します．
	 * </p>
	 */
	public void dispose() {
		configReader.close();
		configWriter.close();
		if (this.childConfigContainer != null) {
			this.childConfigContainer.dispose();
		}
	}

	public void loadBeans() {
		this.initialize();
		this.configInjector.inject(this, true);
	}
	
	public void saveConfig(){
		this.initialize();
		this.configInjector.inject(this, false);		
	}

	/**
	 * コンフィグ名に対応するコンフィグコンテナを検索して返します．
	 * 
	 * @param configName
	 *            コンフィグ名
	 * @return コンフィグコンテナ
	 */
	public ConfigContainer findAllConfigContainer(final String configName) {
		this.initialize();
		ConfigContainer result = ConfigContainerTraversal.forEachChild(this,
				new ConfigContainerHandler<ConfigContainer>() {
					public ConfigContainer proccess(ConfigContainer container) {
						return configName.equals(container.getConfigName()) ? container
								: null;
					}
				});
		return result;
	}

	/**
	 * キーに対応する値を末端のコンフィグコンテナから順番に検索し返します．
	 * 
	 * @param <T>
	 *            値の型
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 値
	 */
	public <T> T findAllConfigValue(final String key, final T defaultValue) {
		this.initialize();
		T result = ConfigContainerTraversal.forEachParent(this,
				new ConfigContainerHandler<T>() {
					public T proccess(ConfigContainer container) {
						T result = container.getConfigValue(key, defaultValue);
						return result;
					}
				});
		return result;
	}

	/**
	 * 子供のコンフィグコンテナを返します．
	 * 
	 * @return コンフィグコンテナ
	 */
	public ConfigContainer getChildConfigContainer() {
		return childConfigContainer;
	}

	/**
	 * コンフィグ名を返します．
	 * 
	 * @return コンフィグ名
	 */
	public String getConfigName() {
		return configName;
	}

	/**
	 * キーに対応する値をコンフィグコンテナから取得します．
	 * 
	 * @param <T>
	 *            値の型
	 * @param key
	 * @param defaultValue
	 *            デフォルト値
	 * @return
	 */
	public <T> T getConfigValue(String key, T defaultValue) {
		this.initialize();
		T result = this.configReader.readConfigValue(key, defaultValue);
		return result;
	}

	/**
	 * 親のコンフィグコンテナを返します．
	 * 
	 * @return コンフィグコンテナ
	 */
	public ConfigContainer getParentConfigContainer() {
		return parentConfigContainer;
	}

	/**
	 * コンフィグコンテナを初期化します．
	 */
	public synchronized void initialize() {
		if (initialized) {
			return;
		}
		configReader.open(configName);
		configWriter.open(configName);
		String env = configReader.readConfigValue("env", null);
		if (env != null) {
			childConfigContainer = (ConfigContainer) s2Container
					.getComponent(ConfigContainer.class);
			childConfigContainer.setConfigName(String.format(
					"%s_%s.properties", configName, env));
			childConfigContainer.initialize();
			childConfigContainer.setParentConfigContainer(this);
		}
		initialized = true;
	}

	/**
	 * キーに対応する値を設定します．
	 * 
	 * @param <T>
	 *            値の型
	 * @param key
	 *            キー
	 * @param value
	 *            値
	 */
	public <T> void putConfigValue(String key, T value) {
		this.initialize();
		this.configWriter.writeConfigValue(key, value);
	}

	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		this.childConfigContainer = childConfigContainer;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void setConfigWriter(ConfigWriter configWriter) {
		this.configWriter = configWriter;
	}

	public void setParentConfigContainer(ConfigContainer parentConfigContainer) {
		this.parentConfigContainer = parentConfigContainer;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
	}

	public void sync() {
		this.configWriter.flash();
	}

	/**
	 * @param configInjector
	 *            the configInjector to set
	 */
	public void setConfigInjector(ConfigInjector configInjector) {
		this.configInjector = configInjector;
	}
}
