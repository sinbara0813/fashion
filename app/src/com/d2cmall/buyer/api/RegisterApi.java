package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class RegisterApi extends BaseApi {

    private String loginCode;
    private String password;
    private String rePassword;
    private String nationCode;
    public String code;
    private String app = "APP.Buyer.Android";

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return Constants.REGISTER_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
