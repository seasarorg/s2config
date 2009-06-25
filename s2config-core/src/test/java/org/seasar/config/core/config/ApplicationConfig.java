package org.seasar.config.core.config;

import org.seasar.config.core.config.annotation.Config;
import org.seasar.config.core.config.annotation.ConfigKey;

@Config(name = "test")
public class ApplicationConfig {
	public String env;

	@ConfigKey(name = "test")
	public String hoge;

	@ConfigKey(name = "app.debug")
	public boolean debug;

	@ConfigKey(name = "xxx")
	public Integer xxx;

        /**
         * readOnlyプロパティがTrueの場合のテスト用
         */
        @ConfigKey(name="read", readOnly = true)
        public String readOnlyTrue;

        @ConfigKey(name = "load", readOnly = false)
        public String readOnlyFalse;
}
