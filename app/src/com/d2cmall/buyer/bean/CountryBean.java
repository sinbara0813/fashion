package com.d2cmall.buyer.bean;

import java.io.Serializable;

/**
 * 国家电话编码Bean类
 * Author: Blend
 * Date: 16/4/28 11:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CountryBean implements Serializable {

    private static final long serialVersionUID = -7601967341566018286L;
    /**
     * value : CN
     * mobileCode : 86
     * cnName : 中国大陆
     * enName : China
     * group : 常用
     * re : ^(86){0,1}-?1[3,4,5,7,8]\d{9}$
     */

    private String value;
    private String mobileCode;
    private String cnName;
    private String enName;
    private String group;
    private String re;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

}

