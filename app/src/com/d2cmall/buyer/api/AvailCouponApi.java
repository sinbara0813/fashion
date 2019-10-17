package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class AvailCouponApi extends BaseApi {

    private String status;

    @Override
    protected String getPath() {
        return Constants.COUPON_COUNT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
