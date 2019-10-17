package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/6/11.
 * Description : KaoLaSaleAfterListApi
 * 考拉商品申请售后时会按仓申请售后,拉取该商品同仓的所有商品
 */

public class KaoLaSaleAfterListApi extends BaseApi {

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    private  long orderItemId;
    @Override
    protected String getPath() {
        return Constants.GET_KAOLA_AFTER_SALE_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
