package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/8.
 */

public class CheckIsRegisterApi extends BaseApi {

    private String loginCode;

    public void setLoginCode(String loginCode){
        this.loginCode=loginCode;
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
