package org.seasar.config.core.container;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.Disposable;

public interface ConfigContainer extends Disposable {

	boolean isLoaded();

	/**
	 * 外部設定ファイルからConfigに設定を読み込みます．
	 */
	void loadToBeans();

	/**
	 * Configから外部設定ファイルに設定を書き込みます．
	 */
	void saveFromBeans();

	/**
	 * コンフィグ名に対応するコンフィグコンテナを検索して返します．
	 * 
	 * @param configName
	 *            コンフィグ名
	 * @return コンフィグコンテナ
	 */
	ConfigContainer findAllConfigContainer(final String configName);

	<T> T findAllConfigValue(final Class<T> resultClass, final String key);

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
	<T> T findAllConfigValue(final Class<T> resultClass, final String key,
		final T defaultValue);

	/**
	 * 子供のコンフィグコンテナを返します．
	 * 
	 * @return コンフィグコンテナ
	 */
	ConfigContainer getChildConfigContainer();

	/**
	 * コンフィグ名を返します．
	 * 
	 * @return コンフィグ名
	 */
	String getConfigName();

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
	<T> T getConfigValue(Class<T> resultClass, String key, T defaultValue);

	<T> T getConfigValue(Class<T> resultClass, String key);

	<T> T getConfigValue(String key, T defaultValue);

	/**
	 * 親のコンフィグコンテナを返します．
	 * 
	 * @return コンフィグコンテナ
	 */
	ConfigContainer getParentConfigContainer();

	/**
	 * コンフィグコンテナを初期化します．
	 */
	void initialize();

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
	<T> void putConfigValue(String key, T value);

	void setChildConfigContainer(ConfigContainer childConfigContainer);

	void setConfigName(String configName);

	void setConfigReader(ConfigReader configReader);

	void setConfigWriter(ConfigWriter configWriter);

	void setParentConfigContainer(ConfigContainer parentConfigContainer);

	void setS2Container(S2Container container);

	/**
	 * 設定ファイルに同期します．
	 */
	void sync();

	/**
	 * @param configInjector
	 *            the configInjector to set
	 */
	void setConfigInjector(ConfigInjector configInjector);

}
