package org.seasar.config.core.util;

import org.seasar.config.core.container.ConfigContainer;
import org.seasar.config.core.container.impl.ConfigContainerImpl;

/**
 * {@link ConfigContainer}をトラバーサルを行うためのクラスです。
 * 
 * @author j5ik2o
 */
public final class ConfigContainerTraversal {
	/**
	 * コンフィグコンテナを処理するためのハンドラです．
	 * 
	 * @author j5ik2o
	 * @param <T>
	 *            ハンドラの戻り値の型
	 */
	public static interface ConfigContainerHandler<T> {
		public T proccess(ConfigContainer container);
	}

	/**
	 * 自分自身のコンテナから子供のコンテナを順番にハンドラに渡して処理します．
	 * 
	 * @param <T>
	 *            ハンドラの戻り値の型
	 * @param rootContainer
	 *            ルートコンテナ
	 * @param handler
	 *            コンテナを処理するハンドラ
	 * @return ハンドラからの戻り値
	 */
	public static <T> T forEachChild(ConfigContainerImpl rootContainer,
			ConfigContainerHandler<T> handler) {
		for (ConfigContainer currentContainer = rootContainer; currentContainer != null; currentContainer =
			currentContainer.getChildConfigContainer()) {
			T result = handler.proccess(currentContainer);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * ルートコンテナの保持している末端のコンテナから親のコンテナを順番にハンドラに渡して処理します．
	 * 
	 * @param <T>
	 *            ハンドラの戻り値の型
	 * @param rootContainer
	 *            ルートコンテナ
	 * @param handler
	 *            コンテナを処理するハンドラ
	 * @return ハンドラからの戻り値
	 */
	public static <T> T forEachParent(ConfigContainerImpl rootContainer,
			ConfigContainerHandler<T> handler) {
		ConfigContainer currentContainer = rootContainer;
		ConfigContainer lastChildContainer = null;
		for (; currentContainer != null; currentContainer =
			currentContainer.getChildConfigContainer()) {
			lastChildContainer = currentContainer;
		}
		for (currentContainer = lastChildContainer; currentContainer != null; currentContainer =
			currentContainer.getParentConfigContainer()) {
			T result = handler.proccess(currentContainer);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
