package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/5/22 15:52
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class OrderAddCart extends BaseApi {

    public String skuIds;
    public String nums;

    @Override
    protected String getPath() {
        return Constants.ORDER_ADD_CART;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
