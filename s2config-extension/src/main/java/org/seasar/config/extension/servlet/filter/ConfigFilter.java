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

public class ConfigFilter implements Filter {

	public void destroy() {
		FilterCommandFactory.clearTargetURIs();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {
		FilterCommand cmd =
			FilterCommandFactory.create(request, response, filterChain);
		cmd.execute();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String targetURIs = filterConfig.getInitParameter("targetURIs");
		String[] targetURIArray = targetURIs.split(",");
		for (String targetURI : targetURIArray) {
			FilterCommandFactory.addTargetURI(targetURI);
		}
	}

}
