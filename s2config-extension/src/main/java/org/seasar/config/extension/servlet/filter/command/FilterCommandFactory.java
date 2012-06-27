package org.seasar.config.extension.servlet.filter.command;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface FilterCommandFactory {
	FilterCommand create(ServletRequest request, ServletResponse response);

	void clearTargetURIs();

	void addTargetURI(String targetURI);

	void addConfigName(String configName);
}
