package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/08 11:51
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageListApi extends BaseApi {


    public void setMajorType(int majorType) {
        this.majorType = majorType;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageSize;
    private Integer p;
    private Integer majorType;
    public long majorTypeTime6;
    public long majorTypeTime7;

    @Override
    protected String getPath() {
        return Constants.MESSAGE_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
