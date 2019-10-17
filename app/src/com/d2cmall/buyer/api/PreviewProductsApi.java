package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 直播预告商品列表
 * Author: zm
 * Date: 2017/03/27 14:41
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class PreviewProductsApi extends BaseApi {

    public long liveId;
    public Integer p;

    @Override
    protected String getPath() {
        return Constants.PREVIEW_PRODUCTS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
