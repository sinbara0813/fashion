package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 10:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartCollectApi extends BaseApi {

    public String ids;

    @Override
    protected String getPath() {
        return Constants.MOVE_COLLECT;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
