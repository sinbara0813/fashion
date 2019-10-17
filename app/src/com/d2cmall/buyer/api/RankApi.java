package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/8.
 */

public class RankApi extends BaseApi {

    private String type;
    private Integer pageNumber;
    private Integer pageSize;

    public void setType(String type) {
        this.type = type;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
