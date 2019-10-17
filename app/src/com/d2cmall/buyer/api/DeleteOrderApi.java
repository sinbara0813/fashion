package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.DELETE_ORDER_URL_V3;

/**
 * Created by rookie on 2017/9/12.
 */

public class DeleteOrderApi extends BaseApi {

    private int orderId;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    protected String getPath() {
        return DELETE_ORDER_URL_V3;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
