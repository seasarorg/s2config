package org.seasar.config.extension.servlet.filter.command;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class AbstractFilterCommand implements FilterCommand {
	protected final ServletRequest request;

	protected final ServletResponse response;

	protected final FilterChain filterChain;

	protected static FilterCommand instance;

	protected AbstractFilterCommand(ServletRequest request,
		ServletResponse response, FilterChain filterChain) {
		this.request = request;
		this.response = response;
		this.filterChain = filterChain;
	}
}
