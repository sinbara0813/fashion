package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/14 17:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarStyleApi extends BaseApi {
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageNumber;
    private Integer pageSize;
    private int tagId;
    @Override
    protected String getPath() {
        return Constants.GET_STAR_STYLE;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
