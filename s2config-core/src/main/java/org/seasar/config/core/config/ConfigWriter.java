package org.seasar.config.core.config;

import java.util.Map;

/**
 * 設定情報を書き込むためのインターフェイスです。
 * 
 * @author j5ik2o
 */
public interface ConfigWriter {
	/**
	 * 設定情報を{@link Map}に変換して返します。
	 * 
	 * @return {@link Map}
	 */
	Map<String, Object> toMap();

	/**
	 * 設定情報を開きます。
	 * 
	 * @param configName
	 *            設定名
	 */
	public void open(String configName);

	/**
	 * 設定値を書き込みます。
	 * 
	 * @param <T>
	 *            設定値の型
	 * @param key
	 *            キー
	 * @param value
	 *            設定値
	 */
	public <T> void writeConfigValue(String key, T value);

	/**
	 * 設定情報をフラッシュします。
	 */
	public void flash();

	/**
	 * 設定情報を閉じます。
	 */
	public void close();
}
