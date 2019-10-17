package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/7.
 */

public class ThemeApi extends BaseApi {
    private Integer tagId;
    private int pageNumber;

    public void setPageNumber(int pageNumber){
        this.pageNumber=pageNumber;
    }


    public void setTagId(int tagId){
        this.tagId=tagId;
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
