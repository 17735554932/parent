package com.teacity.parent.client.support;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.xyf.platform.base.common.RestResult;
import com.xyf.platform.base.plugin.jwt.shiro.JwtAuthenticationToken;
import io.jboot.component.jwt.JwtManager;
import io.jboot.component.shiro.JbootShiroInvokeListener;
import io.jboot.component.shiro.processer.AuthorizeResult;
import io.jboot.utils.StrUtils;
import io.jboot.web.controller.JbootController;
import io.jboot.web.fixedinterceptor.FixedInvocation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import java.util.Map;

/**
 * jwt shiro listener
 * @author wangyq
 *
 */
public class JwtShiroInvokeListener implements JbootShiroInvokeListener {

    private final static Log log = Log.getLog(JwtShiroInvokeListener.class);

    @Override
    public void onInvokeBefore(FixedInvocation inv) {
        JbootController controller = (JbootController) inv.getController();
        String jwtToken = controller.getHeader(JwtManager.me().getHttpHeaderName());

        if (StrUtils.isBlank(jwtToken)) {
            inv.invoke();
            return;
        }

        Map jwtParas = JwtManager.me().getParas();
        String userId = String.valueOf(jwtParas.get("userId"));

        AuthenticationToken token = new JwtAuthenticationToken(userId, jwtToken);

        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onInvokeAfter(FixedInvocation inv, AuthorizeResult result) {
        if (result == null || result.isOk()) {
            inv.invoke();
            return;
        }

        int errorCode = result.getErrorCode();
        switch (errorCode) {
            case AuthorizeResult.ERROR_CODE_UNAUTHENTICATED:
                doProcessUnauthenticated(inv.getController());
                break;
            case AuthorizeResult.ERROR_CODE_UNAUTHORIZATION:
                doProcessuUnauthorization(inv.getController());
            break;
            default:
                doProcessuDefault(inv.getController());
        }
    }

    /**
     * 其他处理
     * @param controller
     */
    private void doProcessuDefault(Controller controller) {
        controller.renderJson(RestResult.buildError("404"));
    }

    /**
     * 没有认证信息处理
     * @param controller
     */
    private void doProcessUnauthenticated(Controller controller) {
        controller.renderJson(RestResult.buildError("401"));
    }

    /**
     * 无授权信息处理
     * @param controller
     */
    private void doProcessuUnauthorization(Controller controller) {
        controller.renderJson(RestResult.buildError("403"));
    }
}
