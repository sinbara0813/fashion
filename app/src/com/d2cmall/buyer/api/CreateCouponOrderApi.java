package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/4 10:29
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CreateCouponOrderApi extends BaseApi {

    public int defId;

    @Override
    protected String getPath() {
        return Constants.CREATE_COUPON_ORDER;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
