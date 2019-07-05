package com.xyf.platform.base.web.base;

import io.jboot.utils.StrUtils;
import io.jboot.web.controller.JbootController;

/**
 * 控制器基类
 * @author wangyq
 *
 */
public class BaseController extends JbootController {
    /**
     * 获取用户ID
     * @return
     */
    public Long getCurrentUserid(){
        String uid=getAttrForStr("appuserid");
        if(StrUtils.isBlank(uid)){
            uid="0";
        }
        return Long.parseLong(uid);
    }
}
