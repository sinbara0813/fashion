package com.d2cmall.buyer.api;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/13 13:19
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashApi extends BaseApi {

    private Integer pageNumber;
    private Integer pageSize;
    public int promotionId;

    @Override
    protected String getPath() {
        return interPath;
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
