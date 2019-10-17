package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2017/9/13.
 */

public class ConfirmReceiveApi extends BaseApi {
    private long orderItemId;

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    @Override
    protected String getPath() {
        return Constants.CONFIRM_RECEIVE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
