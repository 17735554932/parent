package com.teacity.provider;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.teacity.entity.model.Code;
import com.teacity.entity.model.UserLogin;
import com.teacity.parent.base.util.IDUtils;
import io.jboot.aop.annotation.Bean;
import com.teacity.api.UserService;
import com.teacity.entity.model.User;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;
import java.util.Date;

@Bean
@Singleton
public class UserServiceImpl extends JbootServiceBase<User> implements UserService {
    @Override
    public boolean updateUserInfo(String userId, String name, String age, String sex, String occupation) {
        User user = new User();
        user.setAge(Integer.parseInt(age));
        user.setCreatred(new Date());
        user.setOccupation(occupation);
        user.setUserName(name);
        user.setUserId(userId);
        user.setSex(sex);
        return user.saveOrUpdate();
    }

    @Override
    public boolean updateDesc(String userId, String desc) {
        User user = new User();
        user.setUserId(userId);
        user.setDesc(desc);
        return user.saveOrUpdate();
    }

    @Override
    public boolean updateCity(String userId, String city) {
        User user = new User();
        user.setUserId(userId);
        user.setCity(city);
        return user.saveOrUpdate();
    }

    @Override
    @Before(Tx.class)
    public String updateLogin(String relationId, String city, String code) {
        String userId = new UserLoginServiceImpl().login(relationId,city,code);
        new CodeServiceImpl().userCode(userId,code);
        User user = new User();
        user.setUserId(userId);
        user.setCity(city);
        user.saveOrUpdate();
        return userId;
    }

    @Override
    public Record selectUser(String userId) {
        Record record = Db.findFirst("select a.user_name name,a.age,a.sex,a.occupation,a.front_pic frontPic,a.desc,b.code from user a left join user_login b on a.user_id = b.user_id where a.user_id = "+userId);
        return record;
    }
}