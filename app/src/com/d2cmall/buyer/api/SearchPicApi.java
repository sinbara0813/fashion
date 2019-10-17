package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.SEARCH_URL;

/**
 * Created by rookie on 2018/4/18.
 */

public class SearchPicApi extends BaseApi {
    private String picUrl;
    public int pageNumber;

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    protected String getPath() {
        return SEARCH_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
