package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class InsertCartApi extends BaseApi {
    private long skuId;
    private int num;
    private long crowdItemId;

    @Override
    protected String getPath() {
        return Constants.CART_INSERT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCrowdItemId(long crowdItemId) {
        this.crowdItemId = crowdItemId;
    }
}
