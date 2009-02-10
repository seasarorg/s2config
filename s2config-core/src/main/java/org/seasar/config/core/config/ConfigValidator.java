package org.seasar.config.core.config;

import org.seasar.config.core.config.annotation.Config;

/**
 * Configクラス用のバリデータです。
 * 
 * @author kato
 */
public class ConfigValidator {
	/**
	 * クラスがConfigクラスがどうかを返します。
	 * 
	 * @param clazz
	 *            クラス
	 * @return Configクラスの場合はtrue,でない場合はfalse
	 */
	public boolean isValid(Class<?> clazz) {
		Config config = clazz.getAnnotation(Config.class);
		if (config != null) {
			return true;
		}
		return false;
	}
}
