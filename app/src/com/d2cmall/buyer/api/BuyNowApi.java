package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class BuyNowApi extends BaseApi {

    private Integer crowdItemId;
    private int skuId;
    private int num;

    @Override
    protected String getPath() {
        return Constants.BUY_NOW_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setCrowdItemId(int crowdItemId) {
        this.crowdItemId = crowdItemId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
