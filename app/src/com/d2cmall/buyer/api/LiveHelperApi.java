package com.d2cmall.buyer.api;


public class LiveHelperApi extends BaseApi {

    private int number;

    public void setNumber(int number) {
        this.number = number;
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
