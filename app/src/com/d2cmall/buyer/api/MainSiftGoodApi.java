package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/3/22 10:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MainSiftGoodApi extends BaseApi {

    public String topType;
    public int pageNumber;
    public int pageSize=20;
    public int interval=20;

    @Override
    protected String getPath() {
        return Constants.MAIN_SIFT_GOOD;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
