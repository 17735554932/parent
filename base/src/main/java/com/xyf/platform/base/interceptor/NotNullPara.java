package com.xyf.platform.base.interceptor;

import java.lang.annotation.*;

/**
 * 参数不为空
 * @author wangyq
 *
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullPara {

    String[] value();

    String errorRedirect() default "";
}
