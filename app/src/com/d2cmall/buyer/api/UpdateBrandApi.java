package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/9/10.
 */

public class UpdateBrandApi extends BaseApi {

    public int  pageNumber;
    public int  pageSize=20;
    public int topId;

    @Override
    protected String getPath() {
        return Constants.UPDATE_BRAND_CATEGORY_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
