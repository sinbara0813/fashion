package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/25 13:48
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class StagesQueryApi extends BaseApi {
    public String orderSn;
    @Override
    protected String getPath() {
        return Constants.STAGES_QUERY;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
