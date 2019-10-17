package com.d2cmall.buyer.api;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/17 13:56
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class OrderDataApi extends BaseApi {

    public Integer day;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
