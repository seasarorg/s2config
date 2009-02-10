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
