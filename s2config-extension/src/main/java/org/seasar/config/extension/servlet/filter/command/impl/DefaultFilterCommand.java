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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.config.extension.servlet.filter.command.AbstractFilterCommand;
import org.seasar.config.extension.servlet.filter.command.FilterCommand;

/**
 * デフォルトのフィルターコマンドです。
 * 
 * @author j5ik2o
 */
public class DefaultFilterCommand extends AbstractFilterCommand {
	/**
	 * シングルトンなインスタンスを返します。
	 * 
	 * @return {@link DefaultFilterCommand}
	 */
	public static synchronized FilterCommand getInstance() {
		if (instance == null) {
			instance = new DefaultFilterCommand();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.seasar.config.extension.servlet.filter.command.FilterCommand#execute
	 * (javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	public void execute(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(request, response);
	}
}
