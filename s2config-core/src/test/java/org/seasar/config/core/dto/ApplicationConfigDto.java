package org.seasar.config.core.dto;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Config(name = "test")
@Component(instance = InstanceType.SINGLETON)
public class ApplicationConfigDto {
	public String env;

	@ConfigKey(name = "test")
	public String hoge;

	@ConfigKey(name = "app.debug")
	public boolean debug;

	@ConfigKey(name = "xxx")
	public Integer xxx;
}
