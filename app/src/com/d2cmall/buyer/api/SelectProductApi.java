package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/11.
 */

public class SelectProductApi extends BaseApi {

    private String keyword;
    private int page;
    private String sn;

    public void setPage(int page) {
        this.page = page;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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
