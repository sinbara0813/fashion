package com.d2cmall.buyer.api;

public class PostCouponGroupApi extends BaseApi {

    public String code;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}