package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 退货退款提交物流信息
 * Author: Blend
 * Date: 2016/06/29 10:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PostReshipLogisticApi extends BaseApi {

    private long reshipId;
    private String deliveryCorpName;
    private String deliverySn;
    private String memo;
    private String backAccountSn;
    private String backAccountName;

    public String getDeliveryDesc() {
        return deliveryDesc;
    }

    public void setDeliveryDesc(String deliveryDesc) {
        this.deliveryDesc = deliveryDesc;
    }

    private String deliveryDesc;
    public void setDeliveryCorpName(String deliveryCorpName) {
        this.deliveryCorpName = deliveryCorpName;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setReshipId(long reshipId) {
        this.reshipId = reshipId;
    }

    public void setBackAccountSn(String backAccountSn) {
        this.backAccountSn = backAccountSn;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
    }

    @Override
    protected String getPath() {
        return Constants.POST_RESHIP_LOGISTIC_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
