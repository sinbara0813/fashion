package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class FiltrationApi extends BaseApi {

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected String getPath() {
        return Constants.FILTRATION_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
