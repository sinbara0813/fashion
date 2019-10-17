package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/29.
 */

public class CancleExchangeApi extends BaseApi {
    private long exchangeId;

    public long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
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
