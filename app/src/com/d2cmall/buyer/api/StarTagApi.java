package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/14 17:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarTagApi extends BaseApi {
    public void setType(String type) {
        this.type = type;
    }

    private String type;
    @Override
    protected String getPath() {
        return Constants.GET_STAR_TAG;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
