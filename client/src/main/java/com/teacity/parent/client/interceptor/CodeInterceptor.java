package com.teacity.parent.client.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log;
import com.teacity.entity.status.AjaxResult;
import com.teacity.parent.base.common.CodeConsts;
import com.teacity.parent.base.web.base.BaseController;
import io.jboot.Jboot;

/**
 * 通用参数拦截器
 */
public class CodeInterceptor implements Interceptor {
    static Log logger = Log.getLog(CodeInterceptor.class);
    AjaxResult ajaxResult=new AjaxResult();
    @Override
    public void intercept(Invocation inv) {
        BaseController controller = (BaseController) inv.getController();
        String code =inv.getController().getRequest().getParameter("code");
        String codeContent = Jboot.me().getCache().get("teacityLoginCode:",code);
        if(null==codeContent){
            controller.renderJson( ajaxResult.addConfirmError("二维码已过期!", CodeConsts.EXCEPTION));
            return;
        }
    }

}

