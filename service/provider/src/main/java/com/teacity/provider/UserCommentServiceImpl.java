package com.teacity.provider;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.teacity.parent.base.util.IDUtils;
import io.jboot.aop.annotation.Bean;
import com.teacity.api.UserCommentService;
import com.teacity.entity.model.UserComment;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

@Bean
@Singleton
public class UserCommentServiceImpl extends JbootServiceBase<UserComment> implements UserCommentService {


    @Override
    public List<Record> selectComments(String userId, String type) {
        String select = "select a.comment_url url,a.comment_content content,a.created,b.user_name name,b.front_pic frontPic,c.shop_name shopName,a.user_id ";
        String from = "from user_comment a LEFT JOIN user b on a.user_id = b.user_id left join shop c on a.shop_id = c.shop_id ";
        String where = "";
        if(UserComment.COMMENT_TYPE_IMPORTANT.equals(type)){
            where = "where a.type = "+type+"and a.state = 1";
        }else if(UserComment.COMMENT_TYPE_All.equals(type)){
            where = "where a.state = 1 and b.sex != (select sex from user where user_id = "+userId+")";
        }
        return Db.find(select+from+where);
    }

    @Override
    public boolean insertComment(String content, String url, String userId, String shopId, String type) {
        UserComment comment = new UserComment();
        comment.setCommentContent(content);
        comment.setCommentUrl(url);
        comment.setShopId(shopId);
        comment.setUserId(userId);
        comment.setType(Integer.parseInt(type));
        comment.setCreated(new Date());
        comment.setUserCommentId(IDUtils.genUserId());
        return comment.save();
    }
}