package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 拍卖支付url
 * Author: hrb
 * Date: 2016/09/02 10:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class AuctionPayApi extends BaseApi {

    private String id;
    private String orderType;
    private String paymentType;
    private String password;
    public String bank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getPath() {
        return Constants.AUCTION_PAY_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
