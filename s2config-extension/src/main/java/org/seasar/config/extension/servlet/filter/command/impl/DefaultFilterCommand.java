package org.seasar.config.extension.servlet.filter.command.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.config.extension.servlet.filter.command.AbstractFilterCommand;
import org.seasar.config.extension.servlet.filter.command.FilterCommand;

public class DefaultFilterCommand extends AbstractFilterCommand {

	public static synchronized FilterCommand getInstance(
		ServletRequest request, ServletResponse response,
		FilterChain filterChain) {
		if (instance == null) {
			instance = new DefaultFilterCommand(request, response, filterChain);
		}
		return instance;
	}

	protected DefaultFilterCommand(ServletRequest request,
		ServletResponse response, FilterChain filterChain) {
		super(request, response, filterChain);
	}

	public void execute() throws IOException, ServletException {
		filterChain.doFilter(request, response);
	}

}
