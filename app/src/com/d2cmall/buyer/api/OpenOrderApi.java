package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/1/20.
 * Description : OpenOrderApi
 */

public class OpenOrderApi extends BaseApi {

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

    private Integer pageNumber;
    private Integer pageSize;
    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private long tagId;
    private String name;
    @Override
    protected String getPath() {
        return Constants.PRODUCTS_URL;
    }

    @Override
    public Method requestMethod() {
       return Method.GET;
    }
}
