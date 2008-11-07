package org.seasar.config.core.dto;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;

@Config(name = "test")
public class ApplicationConfigDto {

	public String env;

	@ConfigKey(name = "test")
	public String hoge;

	@ConfigKey(name = "app.debug")
	public boolean debug;

}
