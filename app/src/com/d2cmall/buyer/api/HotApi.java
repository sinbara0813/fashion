package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 19:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HotApi extends BaseApi {

    public String type;

    @Override
    protected String getPath() {
        return Constants.GET_HOT_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
