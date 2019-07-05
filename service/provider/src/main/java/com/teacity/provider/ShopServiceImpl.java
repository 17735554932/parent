package com.teacity.provider;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.teacity.api.ShopService;
import com.teacity.entity.model.Shop;
import io.jboot.aop.annotation.Bean;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;
import java.util.List;

@Bean
@Singleton
public class ShopServiceImpl extends JbootServiceBase<Shop> implements ShopService {

    @Override
    public List<Record> selectCityShop(String city) {
        List<Record> records = Db.find("select shop_id shopId,shop_name shopName from shop where city = "+city);
        return records;
    }
}