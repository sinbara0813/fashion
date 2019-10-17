package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class OrdersApi extends BaseApi {

    private Integer index;
    private Integer p;
    private Integer pageSize;
    private String productName;

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    protected String getPath() {
        return Constants.ORDERS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
