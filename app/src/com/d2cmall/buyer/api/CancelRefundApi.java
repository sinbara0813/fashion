package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/9.
 */

public class CancelRefundApi extends BaseApi{
    private int refundId;

    public void setRefundId(int refundId) {
        this.refundId = refundId;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
