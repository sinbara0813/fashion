package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/20 16:09
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainShowApi extends BaseApi {

    public String subModuleName;

    @Override
    protected String getPath() {
        return Constants.GET_MAIN_SHOW_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
