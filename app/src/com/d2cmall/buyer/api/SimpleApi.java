package com.d2cmall.buyer.api;


public class SimpleApi extends BaseApi {

    private Integer pageNumber;
    private Integer pageSize;
    private Method method = Method.GET;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setP(Integer p) {
        this.pageNumber = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
