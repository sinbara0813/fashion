package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/25 11:57
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UserinfoApi extends BaseApi {
    public String loginCode;

    @Override
    protected String getPath() {
        return Constants.USER_SIGN;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
