package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 更换个人主页背景图
 * Author: Blend
 * Date: 2016/06/29 18:53
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateCoverApi extends BaseApi {

    private String background;

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    protected String getPath() {
        return Constants.UPDATE_COVER_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
