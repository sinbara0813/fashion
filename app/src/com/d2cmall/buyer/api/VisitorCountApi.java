package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/11 19:25
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class VisitorCountApi extends BaseApi {

    public void setEvent(String event) {
        this.event = event;
    }

    private String event;

    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
