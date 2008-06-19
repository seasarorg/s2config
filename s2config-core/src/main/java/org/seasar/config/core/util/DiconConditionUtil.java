package org.seasar.config.core.util;

import org.seasar.framework.util.ResourceUtil;

public class DiconConditionUtil {

	private static final String CONVENTION_DICON = "convention.dicon";

	private static final String CUSTOMIZE_DICON = "configCustomize.dicon";

	public static boolean hasConvention() {
		return ResourceUtil.getResourceNoException(CONVENTION_DICON) != null;
	}

	public static boolean hasCustomize() {
		return ResourceUtil.getResourceNoException(CUSTOMIZE_DICON) != null;
	}
}
