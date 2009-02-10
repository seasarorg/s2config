package org.seasar.config.core.config;

import java.util.Map;

/**
 * 設定情報を読み込むためのインターフェイスです。
 * 
 * @author j5ik2o
 */
public interface ConfigReader {
	/**
	 * 設定情報を{@link Map}に変換して返します。
	 * 
	 * @return {@link Map}
	 */
	public Map<String, Object> toMap();

	/**
	 * 設定情報を{@link Map}から読み込みます。
	 * 
	 * @param configResource
	 *            設定情報
	 */
	public void load(Map<String, Object> configResource);

	/**
	 * 設定情報を開きます。
	 * 
	 * @param configName
	 *            設定名
	 */
	public void open(String configName);

	/**
	 * 設定値を読み込みます。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param resultClass
	 *            設定値のクラス
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key,
			T defaultValue);

	/**
	 * 設定値を読み込みます。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param resultClass
	 *            設定値のクラス
	 * @param key
	 *            キー
	 * @return 設定値
	 */
	public <T> T readConfigValue(Class<T> resultClass, String key);

	/**
	 * 設定情報を閉じます。
	 */
	public void close();
}
