package com.d2cmall.buyer.api;


import com.d2cmall.buyer.Constants;


public class SharesApi extends BaseApi {

    private long tagId;
    private Integer p;
    private Integer pageSize;

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected String getPath() {
        return Constants.SHARES_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
