package org.seasar.config.core.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * Config用のCreatorです。
 * 
 * @author j5ik2o
 */
public class ConfigCreator extends ComponentCreatorImpl {
	private static final String NAME_SUFFIX_CONFIG = "Config";

	public ConfigCreator(NamingConvention namingConvention) {
		super(namingConvention);
		setNameSuffix(NAME_SUFFIX_CONFIG);
		setInstanceDef(InstanceDefFactory.SINGLETON);
		setExternalBinding(false);
		setEnableAbstract(false);
		setEnableInterface(false);
	}

	public ComponentCustomizer getConfigCustomizer() {
		return super.getCustomizer();
	}

	public void setConfigCustomizer(ComponentCustomizer customizer) {
		super.setCustomizer(customizer);
	}
}
