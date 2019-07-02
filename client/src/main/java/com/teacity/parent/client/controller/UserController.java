package com.teacity.parent.client.controller;

import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.teacity.api.UserCommentService;
import com.teacity.api.UserService;
import com.teacity.entity.model.User;
import com.teacity.entity.status.AjaxResult;
import com.teacity.parent.base.web.base.BaseController;
import com.teacity.parent.client.interceptor.CodeInterceptor;
import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.web.controller.annotation.RequestMapping;

import java.util.List;

/**
 * describe:
 *
 * @author 白野
 * @date 2019\5\22 0022
 */
@Before(CodeInterceptor.class)
@RequestMapping("/user")
public class UserController extends BaseController {

    @JbootrpcService
    UserService userService;




    private AjaxResult ajaxResult = new AjaxResult();
    static Log log = Log.getLog(UserController.class);


    public void updateUser() {
        String userId = getPara("userId","0");
        String name = getPara("name","1");
        String age = getPara("age","1");
        String sex = getPara("sex","1");
        String occupation = getPara("occupation","1");
        if(userService.updateUserInfo(userId,name,age,sex,occupation)){
            renderJson(ajaxResult.success("nodata", "更新成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "更新失败!"));
        return;
    }

    public void selectUser(){
        String userId = getPara("userId","");
        Record user = userService.selectUser(userId);
        if(null!=user){
            renderJson(ajaxResult.success(user, "查询成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "查询失败!"));
        return;
    }

    public void updateDesc(){
        String userId = getPara("userId","");
        String desc = getPara("desc","1");
        if(userService.updateDesc(userId,desc)){
            renderJson(ajaxResult.success("nodata", "更新成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "更新失败!"));
        return;
    }

    public void updateCity(){
        String userId = getPara("userId","");
        String city = getPara("city","1");
        if(userService.updateCity(userId,city)){
            renderJson(ajaxResult.success("nodata", "更新成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "更新失败!"));
        return;
    }

    public void login(){
        String realtionId = getPara("realtionId","");
        String code = getPara("code","");
        String city = getPara("city","1");
        String userid = userService.updateLogin(realtionId,city,code);
        if(null!=userid){
            renderJson(ajaxResult.success(userid, "更新成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "更新失败!"));
        return;
    }
}
