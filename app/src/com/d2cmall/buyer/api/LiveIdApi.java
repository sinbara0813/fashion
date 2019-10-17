package com.d2cmall.buyer.api;


public class LiveIdApi extends BaseApi {

    public long liveId;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
