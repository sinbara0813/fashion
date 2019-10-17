package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 所有商品列表
 * Author: hrb
 * Date: 2017/01/10 17:38
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DesignAllGoodApi extends BaseApi {

    private Integer pageSize = 10;
    private Integer p;
    private String keyword;

    @Override
    protected String getPath() {
        return Constants.DESIGNER_GOOD_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }


    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
