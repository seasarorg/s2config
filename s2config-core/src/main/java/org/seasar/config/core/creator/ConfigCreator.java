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
package org.seasar.config.core.creator;

import org.seasar.framework.container.ComponentCreator;
import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * Config用の{@link ComponentCreator}です。
 * 
 * @author j5ik2o
 */
public class ConfigCreator extends ComponentCreatorImpl {
	private static final String NAME_SUFFIX_CONFIG = "Config";

	/**
	 * コンストラクタです．
	 * 
	 * @param namingConvention
	 *            {@link NamingConvention}
	 */
	public ConfigCreator(NamingConvention namingConvention) {
		super(namingConvention);
		setNameSuffix(NAME_SUFFIX_CONFIG);
		setInstanceDef(InstanceDefFactory.SINGLETON);
		setExternalBinding(false);
		setEnableAbstract(false);
		setEnableInterface(false);
	}

	/**
	 * Config用の{@link ComponentCustomizer}を返します．
	 * 
	 * @return {@link ComponentCustomizer}
	 */
	public ComponentCustomizer getConfigCustomizer() {
		return super.getCustomizer();
	}

	/**
	 * Config用の{@link ComponentCustomizer}を設定します．
	 * 
	 * @param customizer
	 *            {@link ComponentCustomizer}
	 */
	public void setConfigCustomizer(ComponentCustomizer customizer) {
		super.setCustomizer(customizer);
	}
}
