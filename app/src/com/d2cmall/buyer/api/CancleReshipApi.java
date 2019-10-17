package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/9.
 */

public class CancleReshipApi extends BaseApi {

    private int reshipId;

    public void setReshipId(int reshipId) {
        this.reshipId = reshipId;
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
