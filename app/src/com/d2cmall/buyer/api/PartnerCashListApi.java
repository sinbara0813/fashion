package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/12 13:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerCashListApi extends SimpleApi {

    public void setP(int p) {
        this.pageSize = p;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private int pageNumber;
    private int pageSize;
    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_CASH_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
