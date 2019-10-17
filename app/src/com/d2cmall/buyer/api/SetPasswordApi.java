package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class SetPasswordApi extends BaseApi {

    private String loginCode;
    private String password1;
    private String password2;

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    protected String getPath() {
        return Constants.SET_PASSWORD_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
