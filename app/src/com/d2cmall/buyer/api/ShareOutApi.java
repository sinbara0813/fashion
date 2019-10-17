package com.d2cmall.buyer.api;


public class ShareOutApi extends BaseApi {

    private String param;

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
