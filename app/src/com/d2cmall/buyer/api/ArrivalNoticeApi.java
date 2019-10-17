package com.d2cmall.buyer.api;

public class ArrivalNoticeApi extends BaseApi {

    private String type = "ARRIVAL";

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
