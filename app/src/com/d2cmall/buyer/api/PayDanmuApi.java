package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class PayDanmuApi extends BaseApi {

    private long toMemberId;
    private String password;

    @Override
    protected String getPath() {
        return Constants.PAY_DANMU_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToMemberId(long toMemberId) {
        this.toMemberId = toMemberId;
    }
}
