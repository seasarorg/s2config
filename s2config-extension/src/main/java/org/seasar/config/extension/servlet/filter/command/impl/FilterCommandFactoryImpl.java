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
package org.seasar.config.extension.servlet.filter.command.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.config.extension.servlet.filter.command.FilterCommand;
import org.seasar.config.extension.servlet.filter.command.FilterCommandFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;

public final class FilterCommandFactoryImpl implements FilterCommandFactory {
	private final List<String> targetURIList = CollectionsUtil.newArrayList();

	private final Map<String, Pattern> targetURIPatternMap =
		CollectionsUtil.newHashMap();

	/**
	 * ターゲットURIを追加します。
	 * 
	 * @param targetURI
	 *            ターゲットURI
	 */
	public void addTargetURI(String targetURI) {
		if (targetURI == null) {
			throw new IllegalArgumentException("targetURI is null.");
		}
		targetURIList.add(targetURI);
		Pattern pattern = Pattern.compile(targetURI);
		targetURIPatternMap.put(targetURI, pattern);
	}

	/**
	 * ターゲットURIをクリアします。
	 */
	public void clearTargetURIs() {
		targetURIList.clear();
		targetURIPatternMap.clear();
	}

	private boolean isTargetURI(String uri) {
		for (String targetURI : targetURIList) {
			Pattern pattern = targetURIPatternMap.get(targetURI);
			if (pattern.matcher(uri).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィルターコマンドを生成します。
	 * 
	 * @param request
	 *            {@link ServletRequest}
	 * @param response
	 *            {@link ServletResponse}
	 * @return {@link FilterCommand}
	 */
	public FilterCommand create(ServletRequest request, ServletResponse response) {
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
