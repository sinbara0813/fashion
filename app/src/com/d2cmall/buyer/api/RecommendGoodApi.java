package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/10 17:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommendGoodApi extends BaseApi {

    private long productId;
    private int status;
    private long liveId;

    @Override
    protected String getPath() {
        return Constants.LIVE_ANCHOR_RECOMMEND_PRODUCT;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }
}
