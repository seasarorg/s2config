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
package org.seasar.config.core.util;

import org.seasar.framework.util.ResourceUtil;

/**
 * diconファイルの存在可否を返すクラスです。
 * 
 * @author j5ik2o
 */
public class DiconConditionUtil {
	private static final String CONVENTION_DICON = "convention.dicon";

	private static final String CUSTOMIZE_DICON = "configCustomize.dicon";

	/**
	 * convention.diconがあるかないかを返します。
	 * 
	 * @return ある場合true, ない場合false
	 */
	public static boolean hasConvention() {
		return ResourceUtil.getResourceNoException(CONVENTION_DICON) != null;
	}

	/**
	 * configCustomize.diconがあるかないかを返します。
	 * 
	 * @return ある場合true, ない場合false
	 */
	public static boolean hasCustomize() {
		return ResourceUtil.getResourceNoException(CUSTOMIZE_DICON) != null;
	}
}
