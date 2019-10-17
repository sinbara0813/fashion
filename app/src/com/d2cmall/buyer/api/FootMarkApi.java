package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2018/3/2.
 */

public class FootMarkApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.GET_FOOTMARK_PRODUCT_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
