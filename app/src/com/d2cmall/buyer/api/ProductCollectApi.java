package com.d2cmall.buyer.api;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/26 13:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductCollectApi extends BaseApi {

    public long productId;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
