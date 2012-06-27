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

import java.util.Map;

import org.seasar.config.core.config.ConfigReader;
import org.seasar.config.core.config.ConfigWriter;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.Disposable;

/**
 * @author j5ik2o
 */
public interface ConfigContainer extends Disposable {
	/**
	 * 設定情報を{@link Map}で返します。
	 * 
	 * @return
	 */
	Map<String, Object> getConfigMap();

	/**
	 * {@link Map}に書き込みます。
	 * 
	 * @param resourceMap
	 */
	void saveToMap(Map<String, Map<String, Object>> resourceMap);

	/**
	 * {@link Map}から読み込みます。
	 * 
	 * @param configName
	 * @param resourceMap
	 */
	void loadFromMap(String configName,
			Map<String, Map<String, Object>> resourceMap);

	/**
	 * loadToBeansメソッドが呼ばれたがどうかを返します。
	 * 
	 * @return
	 */
	boolean isLoadedToBeans();

	/**
	 * 外部設定ファイルからJavaBeansに設定を読み込みます。
	 */
	void loadToBeans();

	/**
	 * JavaBeansから外部設定ファイルに設定を書き込みます。
	 */
	void saveFromBeans();

	/**
	 * コンフィグ名に対応するコンフィグコンテナを検索して返します。
	 * 
	 * @param configName
	 *            コンフィグ名
	 * @return コンフィグコンテナ
	 */
	ConfigContainer findAllConfigContainer(final String configName);

	/**
	 * キー対応する設定値を検索して返します。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param resultClass
	 *            設定値のクラス
	 * @param key
	 *            キー
	 * @return 設定値
	 */
	<T> T findAllConfigValue(final Class<T> resultClass, final String key);

	/**
	 * キーに対応する設定値を末端のコンフィグコンテナから順番に検索し返します．
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
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
	 * キーに対応する設定値をコンフィグコンテナから取得します。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	<T> T getConfigValue(Class<T> resultClass, String key, T defaultValue);

	/**
	 * キーに対応する設定値をコンフィグコンテナから取得します。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param resultClass
	 *            設定値のクラス
	 * @param key
	 *            キー
	 * @return 設定値
	 */
	<T> T getConfigValue(Class<T> resultClass, String key);

	/**
	 * キーに対応する設定値をコンフィグコンテナから取得します。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
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
	 * キーに対応する設定値を設定します．
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param key
	 *            キー
	 * @param value
	 *            設定値
	 */
	<T> void putConfigValue(String key, T value);

	/**
	 * 子供の{@link ConfigContainer}を設定します。
	 * 
	 * @param childConfigContainer
	 *            子供の{@link ConfigContainer}
	 */
	void setChildConfigContainer(ConfigContainer childConfigContainer);

	/**
	 * 設定名を設定します。
	 * 
	 * @param configName
	 *            設定名
	 */
	void setConfigName(String configName);

	/**
	 * {@link ConfigReader}を設定します。
	 * 
	 * @param configReader
	 *            {@link ConfigReader}
	 */
	void setConfigReader(ConfigReader configReader);

	/**
	 * {@link ConfigWriter}を設定します。
	 * 
	 * @param configWriter
	 *            {@link ConfigWriter}
	 */
	void setConfigWriter(ConfigWriter configWriter);

	/**
	 * 親の{@link ConfigContainer}を設定します。
	 * 
	 * @param parentConfigContainer
	 *            親の{@link ConfigContainer}
	 */
	void setParentConfigContainer(ConfigContainer parentConfigContainer);

	/**
	 * {@link S2Container}を設定します。
	 * 
	 * @param container
	 *            {@link S2Container}
	 */
	void setS2Container(S2Container container);

	/**
	 * 設定ファイルに同期します．
	 */
	void sync();

	/**
	 * {@link ConfigInjector}を設定します。
	 * 
	 * @param configInjector
	 *            {@link ConfigInjector}
	 */
	void setConfigInjector(ConfigInjector configInjector);
}
