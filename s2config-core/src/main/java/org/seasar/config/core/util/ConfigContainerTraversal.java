package org.seasar.config.core.util;

import org.seasar.config.core.container.ConfigContainer;

public final class ConfigContainerTraversal {

	public static interface ConfigContainerHandler<T> {
		public T proccess(ConfigContainer container);
	}

	public static <T> T forEach(ConfigContainer rootContainer,
			ConfigContainerHandler<T> handler) {
		for (ConfigContainer currentContainer = rootContainer; currentContainer != null; currentContainer = currentContainer
				.getChildConfigContainer()) {
			T result = handler.proccess(currentContainer);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public static <T> T forEachReverse(ConfigContainer rootContainer,
			ConfigContainerHandler<T> handler) {
		ConfigContainer currentContainer = rootContainer;
		ConfigContainer lastChildContainer = null;
		for (; currentContainer != null; currentContainer = currentContainer
				.getChildConfigContainer()) {
			lastChildContainer = currentContainer;
		}
		for (currentContainer = lastChildContainer; currentContainer != null; currentContainer = currentContainer
				.getParentConfigContainer()) {
			T result = handler.proccess(currentContainer);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
