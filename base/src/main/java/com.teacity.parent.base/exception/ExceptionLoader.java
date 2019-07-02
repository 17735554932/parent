package com.teacity.parent.base.exception;

import com.teacity.parent.base.exception.BusinessException;
import io.jboot.exception.JbootException;

/**
 * 异常信息获取类
 * @author wangyq
 *
 */
public class ExceptionLoader {

    public static String read(JbootException e) {
        String message = null;

        if (e.getClass() == BusinessException.class) {
            message = e.getMessage();
        } else if (e.getCause() != null && e.getCause().getClass() == BusinessException.class) {
            message = e.getCause().getMessage();
        } else {
            throw e;
        }

        return message;
    }
}
