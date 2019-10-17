package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/11.
 */

public class BrandFollowApi extends BaseApi {
    private Integer brandId;

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
