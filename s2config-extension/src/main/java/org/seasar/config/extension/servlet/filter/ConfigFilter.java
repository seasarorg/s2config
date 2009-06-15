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
package org.seasar.config.extension.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.config.extension.servlet.filter.command.FilterCommand;
import org.seasar.config.extension.servlet.filter.command.FilterCommandFactory;
import org.seasar.config.extension.servlet.filter.command.impl.FilterCommandFactoryImpl;
import org.seasar.framework.util.tiger.ReflectionUtil;

/**
 * S2Config用フィルターです。
 * 
 * @author j5ik2o
 */
public class ConfigFilter implements Filter {
	private FilterCommandFactory filterCommandFactory;

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		filterCommandFactory.clearTargetURIs();
	}

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		FilterCommand cmd = filterCommandFactory.create(request, response);
		cmd.execute(request, response, filterChain);
	}

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		filterCommandFactory = getFilterCommandFactory(filterConfig);
		String targetURIs = filterConfig.getInitParameter("targetURIs");
		String[] targetURIArray = targetURIs.split(",");
		for (String targetURI : targetURIArray) {
			filterCommandFactory.addTargetURI(targetURI);
		}
	}

	/**
	 * {FilterCommandFactory}のインスタンスを返します。
	 * 
	 * @param filterConfig
	 *            {FilterConfig}
	 * @return {FilterCommandFactory}のインスタンス
	 */
	protected FilterCommandFactory getFilterCommandFactory(
			FilterConfig filterConfig) {
		FilterCommandFactory filterCommandFactory = null;
		Class<FilterCommandFactory> filterCommandFactoryClass =
			getFilterCommandFactoryClass(filterConfig);
		if (filterCommandFactoryClass != null) {
			filterCommandFactory =
				ReflectionUtil.newInstance(filterCommandFactoryClass);
		} else {
			filterCommandFactory = new FilterCommandFactoryImpl();
		}
		return filterCommandFactory;
	}

	/**
	 * {FilterCommandFactory}のクラスを返します。
	 * 
	 * @param filterConfig
	 *            {FilterConfig}
	 * @return {FilterCommandFactory}のクラス
	 */
	protected Class<FilterCommandFactory> getFilterCommandFactoryClass(
			FilterConfig filterConfig) {
		String factoryClassName =
			filterConfig.getInitParameter("factoryClassName");
		Class<FilterCommandFactory> filterCommandFactoryClass =
			ReflectionUtil.forNameNoException(factoryClassName);
		return filterCommandFactoryClass;
	}
}
