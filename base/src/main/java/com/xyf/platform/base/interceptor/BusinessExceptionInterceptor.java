package com.xyf.platform.base.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.xyf.platform.base.common.RestResult;
import io.jboot.exception.JbootException;
import io.jboot.web.controller.JbootController;

/**
 * 业务异常拦截器
 * @author wangyq
 *
 */
public class BusinessExceptionInterceptor implements Interceptor {

	/** 异常内容在模版引擎中的属性TAG */
	public final static String MESSAGE_TAG = "message";

	/** 异常页面 */
	private String exceptionView = "/exception.html";

	public BusinessExceptionInterceptor(String exceptionView) {
		this.exceptionView = exceptionView;
	}

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (JbootException e) {
			if (inv.getTarget() instanceof JbootController) {
				JbootController controller = inv.getTarget();

				if (controller.isAjaxRequest()) {
					RestResult<String> restResult = new RestResult<String>();
					restResult.error(e.getMessage());
					controller.renderJson(restResult);
				} else {
					controller.setAttr(MESSAGE_TAG, e.getMessage()).render(exceptionView);
				}
			}
		}
	}

}
