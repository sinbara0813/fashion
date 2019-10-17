package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductRecommendApi extends BaseApi {

    public long productId;
    public int p;
    public int pageSize;

    @Override
    protected String getPath() {
        return Constants.GET_RECOMMEND_PRODUCT;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
