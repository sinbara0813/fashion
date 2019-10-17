package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2017/10/23.
 */

public class SeriesApi extends BaseApi {
    private long designerId;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize;

    public void setBrandId(long brandId) {
        this.designerId = brandId;
    }

    @Override
    protected String getPath() {
        return Constants.BRAND_SERIES;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
