package org.seasar.config.core.config;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigValue;

@Config("test")
public class ApplicationConfig {

	public String env;

	@ConfigValue("test")
	public String hoge;

	@ConfigValue("app.debug")
	public boolean debug;

}
