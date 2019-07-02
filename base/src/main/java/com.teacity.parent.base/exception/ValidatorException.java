package com.teacity.parent.base.exception;

import io.jboot.exception.JbootException;

/**
 * 校验异常
 * @author wangyq
 *
 */
public class ValidatorException extends JbootException {

	public ValidatorException() {
		super();
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

	protected ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
