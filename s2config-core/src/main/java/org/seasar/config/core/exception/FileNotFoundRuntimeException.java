package org.seasar.config.core.exception;

/**
 * @author kato
 */
@SuppressWarnings("serial")
public class FileNotFoundRuntimeException extends RuntimeException {
	/**
	 * コンストラクタです。
	 */
	public FileNotFoundRuntimeException() {
		super();
	}

	/**
	 * コンストラクタです。
	 * 
	 * @param s
	 * @param throwable
	 */
	public FileNotFoundRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * コンストラクタです。
	 * 
	 * @param s
	 */
	public FileNotFoundRuntimeException(String s) {
		super(s);
	}

	/**
	 * コンストラクタです。
	 * 
	 * @param throwable
	 */
	public FileNotFoundRuntimeException(Throwable throwable) {
		super(throwable);
	}
}
