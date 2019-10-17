package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 批量删除消息接口
 * Author: Blend
 * Date: 2017/03/22 17:45
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class MultiDeleteMessageApi extends BaseApi {
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Override
    protected String getPath() {
        return Constants.MULTI_DELETE_MESSAGE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
