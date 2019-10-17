package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/26 16:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainLiveApi extends BaseApi {

    public int status;

    @Override
    protected String getPath() {
        return Constants.GET_MAIN_LIVE_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
