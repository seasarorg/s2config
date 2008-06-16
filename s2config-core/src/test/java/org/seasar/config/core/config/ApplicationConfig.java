package org.seasar.config.core.config;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;

@Config("test")
public class ApplicationConfig {

	public String env;

	@ConfigKey("test")
	public String hoge;

	@ConfigKey("app.debug")
	public boolean debug;

}
