package com.d2cmall.buyer.api;

public class ComdityCommentListApi extends BaseApi {

    private int p;
    private int pageSize = 5;
    private Integer hasPic;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setHasPic(int hasPic) {
        this.hasPic = hasPic;
    }
}
