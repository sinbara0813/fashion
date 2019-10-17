package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class LiveApi extends BaseApi {

    private String title;
    private String cover;


    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected String getPath() {
        return Constants.CREATE_LIVE_STREAM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
