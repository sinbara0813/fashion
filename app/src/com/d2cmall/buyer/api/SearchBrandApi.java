package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2017/9/14.
 */

public class SearchBrandApi extends BaseApi {
    private String keyword;
    private int pageNumber;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected String getPath() {
        return Constants.DESIGNERS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
