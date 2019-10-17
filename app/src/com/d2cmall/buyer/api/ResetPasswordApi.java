package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/8.
 */

public class ResetPasswordApi extends BaseApi {

    private String loginCode;
    private String password;
    private String confirmPassword;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
