package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 换货提交物流信息
 * Author: Blend
 * Date: 2016/06/29 10:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PostExchangeLogisticApi extends BaseApi {

    private long exchangeId;
    private String deliveryCorpName;
    private String deliverySn;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    private String memo;
    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }


    @Override
    protected String getPath() {
        return Constants.POST_EXCHANGE_LOGISTIC_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
