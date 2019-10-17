package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/10 17:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CrowdGoodApi extends BaseApi {

    private Integer pageSize = 10;
    private Integer pageNumber;
    private Long liveId;
    private String keyword;

    @Override
    protected String getPath() {
        return Constants.LIVE_RECOMMEND_GOOD_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
