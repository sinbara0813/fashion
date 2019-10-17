package com.d2cmall.buyer.api;


import com.d2cmall.buyer.Constants;

public class PhoneCodeLoginApi extends BaseApi {

    private String loginCode;
    private String code;
    private String nationCode;
    private String app = "APP.Buyer.Android";

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return Constants.PHONE_CODE_LOGIN_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
