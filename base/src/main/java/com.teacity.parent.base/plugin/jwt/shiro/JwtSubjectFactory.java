package com.teacity.parent.base.plugin.jwt.shiro;

import com.teacity.parent.base.plugin.jwt.shiro.JwtAuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * jwt 不创建 session
 * @author wangyq
 *
 */
public class JwtSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        if (context.getAuthenticationToken() instanceof JwtAuthenticationToken) {
            // jwt 不创建 session
            context.setSessionCreationEnabled(false);
        }

        return super.createSubject(context);
    }
}
