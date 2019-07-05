package com.xyf.platform.base.common;

import java.io.Serializable;

/**
 * @Title: 公共参数实体类
 * @Package: ${package_name}
 * @Description: ${todo}

 * @date: 2018/3/311:18
 */
public class CommonParam implements Serializable {

    /**
     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
     */
    private static final long serialVersionUID = -1434535419440013822L;
    private String iiod; //   客户端类型        0-安卓，1-苹果，2-微信
    private String d_brand; //     设备品牌
    private String d_platform; //平台及版本
    private String d_model;//设备型号

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private String language;//设备端开发语言
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    private String uid;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;//token
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private String secretKey;//混淆key

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    private String channelid;


    public String getIiod() {
        return iiod;
    }

    public void setIiod(String iiod) {
        this.iiod = iiod;
    }

    public String getD_brand() {
        return d_brand;
    }

    public void setD_brand(String d_brand) {
        this.d_brand = d_brand;
    }

    public String getD_platform() {
        return d_platform;
    }

    public void setD_platform(String d_platform) {
        this.d_platform = d_platform;
    }

    public String getD_model() {
        return d_model;
    }

    public void setD_model(String d_model) {
        this.d_model = d_model;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getV_code() {
        return v_code;
    }

    public void setV_code(String v_code) {
        this.v_code = v_code;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    private String dpi;//设备像素
    private String resolution;//分辨率
    private String v_code;//版本
    private String v_name;//   版本类型
    private String nc; //网络状况
    private String d_code;//设备编号
    private String secret;
    private String ts;



}

