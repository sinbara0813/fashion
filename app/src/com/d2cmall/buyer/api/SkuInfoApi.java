package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class SkuInfoApi extends BaseApi {

    private long productId;
    private long color;
    private long size;

    @Override
    protected String getPath() {
        return String.format(Constants.SKUINFO_URL, productId);
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
