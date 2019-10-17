package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 提交退款申请
 * Author: Blend
 * Date: 2016/06/28 14:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PostRefundApi extends BaseApi {
    private long orderItemId;
    private String refundStatus;
    private String refundReason;
    private String backAccountSn;
    private String backAccountName;
    private String applyAmount;
    private String memo;
    private String evidences;

    public void setAllRefund(int allRefund) {
        this.allRefund = allRefund;
    }

    private int allRefund;

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }


    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public void setBackAccountSn(String backAccountSn) {
        this.backAccountSn = backAccountSn;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setEvidences(String evidences) {
        this.evidences = evidences;
    }


    @Override
    protected String getPath() {
        return Constants.POST_REFUND_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
