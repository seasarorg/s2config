package org.seasar.config.extension.servlet.filter.command;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.config.extension.servlet.filter.command.impl.CoolDeployFilterCommand;
import org.seasar.config.extension.servlet.filter.command.impl.DefaultFilterCommand;
import org.seasar.config.extension.servlet.filter.command.impl.HotDeployFilterCommand;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;

public final class FilterCommandFactory {
	private static List<String> targetURIList = CollectionsUtil.newArrayList();

	private static Map<String, Pattern> targetURIPatternMap =
		CollectionsUtil.newHashMap();

	public static void addTargetURI(String targetURI) {
		if (targetURI == null) {
			throw new IllegalArgumentException("targetURI is null.");
		}
		targetURIList.add(targetURI);
		Pattern pattern = Pattern.compile(targetURI);
		targetURIPatternMap.put(targetURI, pattern);
	}

	public static void clearTargetURIs() {
		targetURIList.clear();
		targetURIPatternMap.clear();
	}

	private static boolean isTargetURI(String uri) {
		for (String targetURI : targetURIList) {
			Pattern pattern = targetURIPatternMap.get(targetURI);
			if (pattern.matcher(uri).matches()) {
				return true;
			}
		}
		return false;
	}

	public static FilterCommand create(ServletRequest request,
		ServletResponse response) {
		if (!(request instanceof HttpServletRequest)
			|| !(response instanceof HttpServletResponse)) {
			return DefaultFilterCommand.getInstance();
		}
		final HttpServletRequest hrequest = (HttpServletRequest) request;
		String uri = hrequest.getRequestURI();
		if (!isTargetURI(uri)) {
			return DefaultFilterCommand.getInstance();
		}
		if (HotdeployUtil.isHotdeploy()) {
			return HotDeployFilterCommand.getInstance();
		}
		return CoolDeployFilterCommand.getInstance();

	}
}
