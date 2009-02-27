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

import org.seasar.config.core.config.annotation.Config;

/**
 * Configクラス用のバリデータです。
 * 
 * @author kato
 */
public class ConfigValidator {
	/**
	 * クラスがConfigクラスがどうかを返します。
	 * 
	 * @param clazz
	 *            クラス
	 * @return Configクラスの場合はtrue,でない場合はfalse
	 */
	public boolean isValid(Class<?> clazz) {
		Config config = clazz.getAnnotation(Config.class);
		if (config != null) {
			return true;
		}
		return false;
	}
}
