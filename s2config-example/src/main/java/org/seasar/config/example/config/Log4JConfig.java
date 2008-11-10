package org.seasar.config.example.config;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;

@Config(name = "log4j")
public final class Log4JConfig {

	@ConfigKey(name = "log4j.category.org.seasar")
	public String category;

}
