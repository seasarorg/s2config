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

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.config.core.container.ConfigContainer;
import org.seasar.config.extension.servlet.filter.command.FilterCommand;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * ホットデプロイフィルター用のコマンド
 * 
 * @author j5ik2o
 * @author happy_ryo
 */
public class HotDeployFilterCommand extends DefaultFilterCommand {
	/**
	 * コンフィグ名のセッションキーです。
	 */
	private static final String CONFIG_NAME =
		"org.seasar.config.extension.ConfigName";

	/**
	 * コンフィグリソースのセッションキーです。
	 */
	private static final String CONFIG_RESOURCE =
		"org.seasar.config.extension.ConfigResource";

	/**
	 * シングルトンなインスタンスを返します。
	 * 
	 * @return {@link DefaultFilterCommand}
	 */
	public static synchronized FilterCommand getInstance() {
		if (instance == null) {
			instance = new HotDeployFilterCommand();
		}
		return instance;
	}

	/**
	 * @param request
	 * @param response
	 * @param filterChain
	 * @return FilterCommand
	 */
	public static synchronized FilterCommand getInstance(
			ServletRequest request, ServletResponse response,
			FilterChain filterChain) {
		if (instance == null) {
			instance = new HotDeployFilterCommand();
		}
		return instance;
	}

	private Map<String, Map<String, Object>> configResource;

	private String configName;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.extension.servlet.filter.command.impl.DefaultFilterCommand
	 * #execute(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void execute(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		ConfigContainer configContainer =
			SingletonS2Container.getComponent(ConfigContainer.class);
		synchronized (configContainer.getClass()) {
			if (configResource == null) {
				configContainer.loadToBeans();
			} else {
				configContainer.loadFromMap(configName, configResource);
				configContainer.loadToBeans();
			}
		}
		super.execute(request, response, filterChain);
		configResource = CollectionsUtil.newHashMap();
		synchronized (configContainer.getClass()) {
			configContainer.saveFromBeans();
			configContainer.saveToMap(configResource);
		}
		configName = configContainer.getConfigName();
	}

}
