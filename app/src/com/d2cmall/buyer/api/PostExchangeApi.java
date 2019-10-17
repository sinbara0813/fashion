package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 提交换货申请
 * Author: Blend
 * Date: 2016/06/28 11:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PostExchangeApi extends BaseApi {

    private String orderSn;
    private long orderId;
    private long orderItemId;
    private int quantity;
    private String exchangeReason;
    private String memo;

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExchangeReason(String exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    protected String getPath() {
        return Constants.POST_EXCHANGE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
