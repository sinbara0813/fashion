package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2018/1/2.
 */

public class AnchorInfoApi extends BaseApi {
    private long liveId;

    public long getLiveId() {
        return liveId;
    }

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
