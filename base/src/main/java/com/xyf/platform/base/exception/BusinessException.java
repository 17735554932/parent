package com.xyf.platform.base.exception;

import io.jboot.exception.JbootException;

/**
 * 业务异常
 * @author wangyq
 *
 */
public class BusinessException extends JbootException {

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
