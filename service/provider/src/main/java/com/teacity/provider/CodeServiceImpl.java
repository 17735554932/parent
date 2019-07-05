package com.teacity.provider;

import com.jfinal.plugin.activerecord.Db;
import com.teacity.api.CodeService;
import com.teacity.entity.model.Code;
import io.jboot.aop.annotation.Bean;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

@Bean
@Singleton
public class CodeServiceImpl extends JbootServiceBase<Code> implements CodeService {
    @Override
    public boolean isUsed(String code) {
        Integer rows = Db.queryInt("select count(1) from code where state = 0 and code = "+code);
        if(rows>0){
            return true;
        }
        return false;
    }

    @Override
    public void userCode(String userId, String code) {
        Db.update("update code set state = 1,user_id = "+userId+" where code = "+code);
    }
}