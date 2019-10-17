package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class GetCouponApi extends BaseApi {

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getPath() {
        return Constants.GET_COUPON_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
