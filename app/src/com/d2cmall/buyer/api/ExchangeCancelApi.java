package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/9.
 */

public class ExchangeCancelApi extends BaseApi {
    private int exchangeId;

    public void setExchangeId(int exchangeId) {
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
