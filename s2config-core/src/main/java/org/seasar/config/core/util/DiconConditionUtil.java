package org.seasar.config.core.util;

import org.seasar.framework.util.ResourceUtil;

public class DiconConditionUtil {

	private static final String CONVENTION_DICON = "convention.dicon";

	public static boolean hasConvention() {
		return ResourceUtil.getResourceNoException(CONVENTION_DICON) != null;
	}

}
