package com.d2cmall.buyer.api;


public class PointProductExchangeApi extends BaseApi {

    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
