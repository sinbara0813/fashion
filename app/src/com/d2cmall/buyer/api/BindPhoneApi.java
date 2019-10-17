package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class BindPhoneApi extends BaseApi {

    private String loginCode;
    private String code;
    private String nationCode;
    private String password1;
    private String password2;

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    protected String getPath() {
        return Constants.BIND_MEMBER_PHONE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }
}
