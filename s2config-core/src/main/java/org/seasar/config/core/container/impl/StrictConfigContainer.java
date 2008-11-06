package org.seasar.config.core.container.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.seasar.config.core.container.ConfigContainer;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class StrictConfigContainer extends ConfigContainerWrapper {

	@Binding(bindingType = BindingType.NONE)
	public void setChildConfigContainer(ConfigContainer childConfigContainer) {
		super.setChildConfigContainer(childConfigContainer);
	}

	public StrictConfigContainer(ConfigContainer configContainer) {
		super(configContainer);
	}

	public String getConfigString(String key, String defaultValue) {
		return getConfigValue(String.class, key, defaultValue);
	}

	public BigDecimal getConfigBigDecimal(String key, BigDecimal defaultValue) {
		return getConfigValue(BigDecimal.class, key, defaultValue);
	}

	public Integer getConfigInteger(String key, Integer defaultValue) {
		return getConfigValue(Integer.class, key, defaultValue);
	}

	public Long getConfigLong(String key, Long defaultValue) {
		return getConfigValue(Long.class, key, defaultValue);
	}

	public Float getConfigFloat(String key, Float defaultValue) {
		return getConfigValue(Float.class, key, defaultValue);
	}

	public Double getConfigDouble(String key, Double defaultValue) {
		return getConfigValue(Double.class, key, defaultValue);
	}

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
