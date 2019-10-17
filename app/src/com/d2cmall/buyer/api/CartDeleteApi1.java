package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 10:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartDeleteApi1 extends BaseApi {

    public String ids;

    @Override
    protected String getPath() {
        return Constants.CART_DELETE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
