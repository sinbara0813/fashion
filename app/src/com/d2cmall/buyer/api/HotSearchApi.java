package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.HOT_SEARCH_URL;

/**
 * Created by rookie on 2017/9/11.
 */

public class HotSearchApi extends BaseApi {
    private int subModuleCategoryId;

    public void setSubModuleId(int subModuleId) {
        this.subModuleCategoryId = subModuleId;
    }

    @Override
    protected String getPath() {
        return HOT_SEARCH_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
