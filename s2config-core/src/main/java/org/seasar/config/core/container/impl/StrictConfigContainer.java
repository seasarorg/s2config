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
package org.seasar.config.core.container.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.seasar.config.core.container.ConfigContainer;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

/**
 * Strictな{@link ConfigContainer}です。
 * <p>
 * 主にdiconファイル上で{@link ConfigContainer}を扱うためのクラスです．
 * </p>
 * 
 * @author j5ik2o
 */
public class StrictConfigContainer extends ConfigContainerWrapper {
	/*
	 * (非 Javadoc)
	 * @seeorg.seasar.config.core.container.impl.ConfigContainerWrapper#
	 * setChildConfigContainer(org.seasar.config.core.container.ConfigContainer)
	 */
	@Override
	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		super.setChildConfigContainer(childConfigContainer);
	}

	/**
	 * コンストラクタです。
	 * 
	 * @param configContainer
	 */
	public StrictConfigContainer(ConfigContainer configContainer) {
		super(configContainer);
	}

	/**
	 * {@link String}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public String getConfigString(String key, String defaultValue) {
		return getConfigValue(String.class, key, defaultValue);
	}

	/**
	 * {@link BigDecimal}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public BigDecimal getConfigBigDecimal(String key, BigDecimal defaultValue) {
		return getConfigValue(BigDecimal.class, key, defaultValue);
	}

	/**
	 * {@link Integer}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public Integer getConfigInteger(String key, Integer defaultValue) {
		return getConfigValue(Integer.class, key, defaultValue);
	}

	/**
	 * {@link Long}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public Long getConfigLong(String key, Long defaultValue) {
		return getConfigValue(Long.class, key, defaultValue);
	}

	/**
	 * {@link Float}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public Float getConfigFloat(String key, Float defaultValue) {
		return getConfigValue(Float.class, key, defaultValue);
	}

	/**
	 * {@link Double}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public Double getConfigDouble(String key, Double defaultValue) {
		return getConfigValue(Double.class, key, defaultValue);
	}

	/**
	 * {@link Short}型の設定値を返します。
	 * 
	 * @param key
	 *            キー
	 * @param defaultValue
	 *            デフォルト値
	 * @return 設定値
	 */
	public Short getConfigShort(String key, Short defaultValue) {
		return getConfigValue(Short.class, key, defaultValue);
	}

	public BigInteger getConfigBigInteger(String key, BigInteger defaultValue) {
		return getConfigValue(BigInteger.class, key, defaultValue);
	}

	public Byte getConfigByte(String key, Byte defaultValue) {
		return getConfigValue(Byte.class, key, defaultValue);
	}

	public Boolean getConfigBoolean(String key, Boolean defaultValue) {
		return getConfigValue(Boolean.class, key, defaultValue);
	}
}
