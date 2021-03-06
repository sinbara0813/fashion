package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/12 13:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportDeleteApi extends BaseApi {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    @Override
    protected String getPath() {
        return Constants.PRODUCT_REPORT_DELETE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
