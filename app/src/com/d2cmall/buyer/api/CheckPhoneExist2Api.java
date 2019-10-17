package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class CheckPhoneExist2Api extends BaseApi {

    private String loginCode;
    private String nationCode;

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return Constants.CHECK_PHONE_EXIST2_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
