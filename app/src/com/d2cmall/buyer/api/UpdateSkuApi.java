package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/24 18:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateSkuApi extends BaseApi {

    public long cartItemId;
    public long skuId;

    @Override
    protected String getPath() {
        return Constants.UPDATE_SKU;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
