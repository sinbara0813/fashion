package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class SetPayPwdApi extends BaseApi {

    private String newPassword;
    private String confirmPassword;
    private String mobile;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword = newPassword1;
    }

    public void setNewPassword2(String newPassword2) {
        this.confirmPassword = newPassword2;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    protected String getPath() {
        return Constants.SET_PAY_PASSWORD_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
