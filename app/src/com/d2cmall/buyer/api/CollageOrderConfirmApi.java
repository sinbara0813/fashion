package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/26 10:38
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CollageOrderConfirmApi extends BaseApi {

    public int skuId;
    public int num;
    public int collageId;
    public Integer groupId;

    @Override
    protected String getPath() {
        return Constants.COLLAGE_ORDER_CONFIRM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
