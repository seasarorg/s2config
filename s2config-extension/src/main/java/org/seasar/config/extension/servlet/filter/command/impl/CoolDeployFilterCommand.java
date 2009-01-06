package org.seasar.config.extension.servlet.filter.command.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.config.core.container.ConfigContainer;
import org.seasar.config.extension.servlet.filter.command.FilterCommand;
import org.seasar.framework.container.SingletonS2Container;

public class CoolDeployFilterCommand extends DefaultFilterCommand {
	public static synchronized FilterCommand getInstance() {
		if (instance == null) {
			instance = new CoolDeployFilterCommand();
		}
		return instance;
	}

	public static boolean initialized;

	@Override
	public void execute(ServletRequest request, ServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {
		if (!initialized) {
			ConfigContainer configContainer =
				SingletonS2Container.getComponent(ConfigContainer.class);
			synchronized (configContainer.getClass()) {
				configContainer.loadToBeans();
				initialized = true;
			}
		}
		super.execute(request, response, filterChain);
	}

}
