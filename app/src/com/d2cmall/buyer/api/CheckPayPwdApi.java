package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class CheckPayPwdApi extends BaseApi {

    private String password;

    public void setPassWord(String passWord) {
        this.password = passWord;
    }

    @Override
    protected String getPath() {
        return Constants.CHECK_PAY_PASSWORD_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
