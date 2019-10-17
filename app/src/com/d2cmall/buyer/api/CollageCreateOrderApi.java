package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/27 15:31
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CollageCreateOrderApi extends BaseApi {

    public Double longitude;
    public Double latitude;
    public String quantity;
    public String tempId;
    public long addressId;
    public String skuId;
    public Integer groupId;
    public Integer parent_id;
    public int collageId;
    public String drawee;

    @Override
    protected String getPath() {
        return Constants.COLLAGE_ORDER_CREATE;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
