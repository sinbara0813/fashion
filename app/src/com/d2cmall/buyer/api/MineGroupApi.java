package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2018/6/27.
 */

public class MineGroupApi extends BaseApi {

    private Integer index;
    private Integer pageNumber;
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int status) {
        this.index = status;
    }

    @Override
    protected String getPath() {
        return Constants.GROUP_BUY_MINE;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
