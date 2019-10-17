package com.d2cmall.buyer.api;


public class MainSubApi extends BaseApi {

    private Integer p;
    private Integer pageSize;
    private Integer version;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
