package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/11.
 */

public class FollowApi extends BaseApi {
    private long toMemberId;

    public void setToMemberId(long toMemberId) {
        this.toMemberId = toMemberId;
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
