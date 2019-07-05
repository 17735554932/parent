package com.teacity.provider;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.teacity.api.UserLoginService;
import com.teacity.entity.model.UserLogin;
import com.xyf.platform.base.util.IDUtils;
import io.jboot.Jboot;
import io.jboot.aop.annotation.Bean;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;
import java.util.Date;

@Bean
@Singleton
public class UserLoginServiceImpl extends JbootServiceBase<UserLogin> implements UserLoginService {
    @Override
    public String login(String relationId, String city, String code) {
        Record record = Db.findFirst("select user_id userId from user_login where relation_id = "+relationId);
        if(null==record){
            String userId = IDUtils.genUserId();
            UserLogin userLogin = new UserLogin();
            userLogin.setUserId(userId);
            userLogin.setUserLoginId(IDUtils.generate());
            userLogin.setLastLoginTime(new Date());
            userLogin.setCode(code);
            userLogin.save();
            Jboot.me().getCache().put("teacityLoginCode:",""+code,code,1800);
            return userId;
        }else{
            String userId = record.get("userId");
            Db.update("update user_login set last_login_time = "+new Date()+",code = "+code+" where user_id = " + userId);
            Jboot.me().getCache().put("teacityLoginCode:",code,code,1800);
            return userId;
        }
    }
}