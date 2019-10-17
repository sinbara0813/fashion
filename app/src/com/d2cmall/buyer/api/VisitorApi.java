package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/11 19:25
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class VisitorApi extends BaseApi {
    private Integer pageSize;
    private Integer pageCount;

    public void setEvent(String event) {
        this.event = event;
    }

    private String event;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    private int targetId;

    public void setP(Integer p) {
        this.pageCount = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_VISITOR_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
