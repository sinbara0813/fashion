package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 提交退货退款申请
 * Author: Blend
 * Date: 2016/06/28 11:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PostReshipApi extends BaseApi {

    private long orderItemId;
    private long actualQuantity;
    private String reshipReason;
    private Integer received;
    private String backAccountSn;
    private String backAccountName;
    private String memo;
    private String evidences;
    private String applyAmount;
   // private long quantity;


    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }



    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }


//    public void setQuantity(long quantity) {
//        this.quantity = quantity;
//    }

    public void setReceiveQuantity(long receiveQuantity) {
        this.actualQuantity = receiveQuantity;
    }

    public void setReshipReason(String reshipReason) {
        this.reshipReason = reshipReason;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public void setBackAccountSn(String backAccountSn) {
        this.backAccountSn = backAccountSn;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setEvidences(String evidences) {
        this.evidences = evidences;
    }

    @Override
    protected String getPath() {
        return Constants.POST_RESHIP_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
