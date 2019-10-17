package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class CancelOrderApi extends BaseApi {

    private String orderSn;

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    @Override
    protected String getPath() {
        return Constants.CANCEL_ORDER_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
