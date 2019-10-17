package com.d2cmall.buyer.api;

/**
 * 重新开店
 */

public class ReopenStoreApi extends BaseApi {
    private Integer status=-1;
    private int pageNumber;

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageSize;

    public void setPageNumber(int pageNumber){
        this.pageNumber=pageNumber;
    }


    public void setStatus(int status){
        this.status=status;
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
