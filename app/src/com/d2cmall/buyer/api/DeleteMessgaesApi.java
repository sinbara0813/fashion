package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/09 15:08
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class DeleteMessgaesApi extends BaseApi {

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Override
    protected String getPath() {
        return Constants.MESSAGES_DELETE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
