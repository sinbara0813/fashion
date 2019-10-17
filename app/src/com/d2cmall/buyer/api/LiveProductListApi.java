package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 直播商品列表api
 * Author: hrb
 * Date: 2016/08/02 17:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveProductListApi extends BaseApi {

    private long designerId;
    private Integer pageSize = 10;
    private Integer p;
    private long liveId;

    @Override
    protected String getPath() {
        return Constants.LIVE_PRODUCT_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setDesignerId(long designerId) {
        this.designerId = designerId;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }
}
