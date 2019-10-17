package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class PayPresentApi extends BaseApi {

    private long liveId;
    private int presentId;
    private long toMemberId;
    private Integer quantity;
    private String password;

    @Override
    protected String getPath() {
        return Constants.PAY_PRESENT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPresentId(int presentId) {
        this.presentId = presentId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setToMemberId(long toMemberId) {
        this.toMemberId = toMemberId;
    }
}
