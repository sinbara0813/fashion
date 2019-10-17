package com.d2cmall.buyer.api;


public class HostInfoApi extends BaseApi {

    private long liveId;

    public void setLiveId(long liveId) {
        this.liveId = liveId;
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
