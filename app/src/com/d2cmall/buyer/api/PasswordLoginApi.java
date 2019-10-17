package com.d2cmall.buyer.api;


import com.d2cmall.buyer.Constants;

public class PasswordLoginApi extends BaseApi {

    private String loginCode;
    private String password;
    private String nationCode;
    private String app = "APP.Buyer.Android";
    private String loginDevice;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return Constants.PASSWORD_LOGIN_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
