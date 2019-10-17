package com.d2cmall.buyer.api;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/2 17:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpGoodApi extends BaseApi {

    private Integer pageNumber;
    private Integer pageSize;
    public int topId;

    @Override
    protected String getPath() {
        return "/v3/api/page/newup/goods";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
