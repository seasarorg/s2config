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

	/**
	 * 設定情報を開いているかどうかを返します。
	 * 
	 * @return　開いている場合true
	 */
	public boolean isOpened();
}
