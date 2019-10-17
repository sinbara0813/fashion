package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/14 17:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class WardrobeListApi extends BaseApi {

    private Integer pageNumber;
    private Integer pageSize;
    private String topName;
    private String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }
    @Override
    protected String getPath() {
        return Constants.WARDROBE_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
