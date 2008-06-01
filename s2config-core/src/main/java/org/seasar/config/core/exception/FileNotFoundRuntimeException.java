package org.seasar.config.core.exception;

public class FileNotFoundRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotFoundRuntimeException() {
		super();
	}

	public FileNotFoundRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public FileNotFoundRuntimeException(String s) {
		super(s);
	}

	public FileNotFoundRuntimeException(Throwable throwable) {
		super(throwable);
	}

}
