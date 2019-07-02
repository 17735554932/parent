package com.teacity.entity.status;

import java.io.Serializable;

public class AjaxResult implements Serializable{

	private static final long serialVersionUID = 6439646269084700779L;

	private int code = 200;


	// 返回的中文消息
	private String message;

	// 成功时携带的数据
	private Object data;
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	// 校验错误
	public boolean hasError() {
		return this.code != 200;
	}

	// 添加错误，用于alertError
	public AjaxResult addError(String c,String message) {
		this.message = message;
		this.code = 500;
		this.data=null;
		return this;
	}

	/**
	 * 用于Confirm的错误信息
	 * @param message
	 * @return {AjaxResult}
	 */
	public AjaxResult addConfirmError(String message,int code) {
		this.message = message;
		this.code = code;
		this.data=null;
		return this;
	}

	/**
	 * 封装成功时的数据
	 * @param data
	 * @return {AjaxResult}
	 */
	public AjaxResult success(Object data,String message) {
		this.data = data;
		this.code = 200;
		this.message=message;
		return this;
	}

}
