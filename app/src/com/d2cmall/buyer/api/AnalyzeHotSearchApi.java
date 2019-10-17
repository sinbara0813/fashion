package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class AnalyzeHotSearchApi extends BaseApi {

    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @Override
    protected String getPath() {
        return Constants.ANALYZE_HOT_SEARCH_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
