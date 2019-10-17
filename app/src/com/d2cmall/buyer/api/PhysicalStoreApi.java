package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/10/10.
 */

public class PhysicalStoreApi extends BaseApi {
    private String province;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private int pageNumber;

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
