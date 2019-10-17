package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class CheckPhoneExistApi extends BaseApi {

    private String loginCode;
    //private String nationCode;

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

//    public void setNationCode(String nationCode) {
//        this.nationCode = nationCode;
//    }

    //CHECK_PHONE_EXIST_URL
    @Override
    protected String getPath() {
        return Constants.RESET_PASSWORD_EXIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
