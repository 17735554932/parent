package com.teacity.parent.client.controller;

import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.teacity.api.ShopService;
import com.teacity.api.UserCommentService;
import com.teacity.entity.status.AjaxResult;
import com.teacity.parent.client.interceptor.CodeInterceptor;
import com.xyf.platform.base.web.base.BaseController;
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
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @JbootrpcService
    UserCommentService userCommentService;
    @JbootrpcService
    ShopService shopService;

    private AjaxResult ajaxResult = new AjaxResult();
    static Log log = Log.getLog(CommentController.class);


    public void selectIndexComment() {
        String userId = getPara("userId","");
        String type = getPara("type","1");
        List<Record> recordList = userCommentService.selectComments(userId,type);
        if(recordList.size()>0){
            renderJson(ajaxResult.success(recordList, "查询成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "查询无记录!"));
        return;
    }

    public void insertComment(){
        String content = getPara("content");
        String url = getPara("url");
        String userId = getPara("userId");
        String shopId = getPara("shopId");
        String type = getPara("type");
        if(userCommentService.insertComment(content,url,userId,shopId,type)){
            renderJson(ajaxResult.success("nodata", "发布成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "发布失败!"));
        return;
    }
    public void getShop(){
        String city = getPara("city","长治");
        List<Record> shops = shopService.selectCityShop(city);
        if(shops.size()>0){
            renderJson(ajaxResult.success(shops, "查询成功!"));
            return;
        }
        renderJson(ajaxResult.success("nodata", "暂无!"));
        return;
    }
}
