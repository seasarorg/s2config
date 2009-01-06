package org.seasar.config.extension.servlet.filter.command;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface FilterCommand {
	void execute(ServletRequest request, ServletResponse response,
		FilterChain filterChain) throws IOException, ServletException;
}
