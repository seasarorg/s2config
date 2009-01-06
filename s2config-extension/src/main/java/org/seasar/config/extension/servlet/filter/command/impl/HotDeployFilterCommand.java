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

	public static synchronized FilterCommand getInstance(
		ServletRequest request, ServletResponse response,
		FilterChain filterChain) {
		if (instance == null) {
			instance = new HotDeployFilterCommand();
		}
		return instance;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(ServletRequest request, ServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {
		HttpSession session;
		Map<String, Map<String, Object>> configResource;
		ConfigContainer configContainer =
			SingletonS2Container.getComponent(ConfigContainer.class);
		session = getSession(request);

		configResource =
			(Map<String, Map<String, Object>>) session
				.getAttribute(CONFIG_RESOURCE);
		String configName = (String) session.getAttribute(CONFIG_NAME);
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
		session.setAttribute(CONFIG_RESOURCE, configResource);
		session.setAttribute(CONFIG_NAME, configContainer.getConfigName());

	}

	private HttpSession getSession(ServletRequest request) {
		HttpSession session;
		session = ((HttpServletRequest) request).getSession(false);
		if (session == null) {
			session = ((HttpServletRequest) request).getSession(true);
		}
		return session;
	}

}
