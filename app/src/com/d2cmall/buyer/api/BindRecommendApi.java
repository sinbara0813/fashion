package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class BindRecommendApi extends BaseApi {

    private String recCode;

    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    @Override
    protected String getPath() {
        return Constants.BIND_RECOMMEND_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
