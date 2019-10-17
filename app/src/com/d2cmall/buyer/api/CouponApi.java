package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class CouponApi extends BaseApi {

    private String status;
    private Integer p;
    private Integer pageSize;

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected String getPath() {
        return Constants.COUPONS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
