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

/**
 * S2Config用フィルターです。
 * 
 * @author j5ik2o
 */
public class ConfigFilter implements Filter {

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		FilterCommandFactory.clearTargetURIs();
	}

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {
		FilterCommand cmd = FilterCommandFactory.create(request, response);
		cmd.execute(request, response, filterChain);
	}

	/*
	 * (非 Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		String targetURIs = filterConfig.getInitParameter("targetURIs");
		String[] targetURIArray = targetURIs.split(",");
		for (String targetURI : targetURIArray) {
			FilterCommandFactory.addTargetURI(targetURI);
		}
	}

}
