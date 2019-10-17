package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 13:47
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainRecommendApi extends BaseApi {

    public String subModuleName;
    public int p;
    public int pageSize=10;

    @Override
    protected String getPath() {
        return Constants.GET_MAIN_RECOMMEND_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
