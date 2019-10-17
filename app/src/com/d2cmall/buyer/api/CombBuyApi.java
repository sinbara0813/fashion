package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 组合商品购物api
 * Author: hrb
 * Date: 2016/06/29 14:18
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombBuyApi extends BaseApi {

    private long productCombId;
    private int num;
    private String skuId;

    @Override
    protected String getPath() {
        return Constants.COMB_BUY_NOW_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setProductCombId(long productCombId) {
        this.productCombId = productCombId;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

}
