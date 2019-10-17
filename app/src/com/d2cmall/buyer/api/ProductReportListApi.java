package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/12 13:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportListApi extends BaseApi {
    private int p;

    public void setP(int p) {
        this.p = p;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize;

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private long memberId;
    private int status;

    @Override
    protected String getPath() {
        return Constants.PRODUCT_REPORT_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
