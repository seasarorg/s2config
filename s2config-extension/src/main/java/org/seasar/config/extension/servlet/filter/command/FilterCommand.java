package org.seasar.config.extension.servlet.filter.command;

import java.io.IOException;

import javax.servlet.ServletException;

public interface FilterCommand {
	void execute() throws IOException, ServletException;
}
