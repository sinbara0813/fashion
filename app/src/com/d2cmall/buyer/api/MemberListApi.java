package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 直播用户列表
 * Author: hrb
 * Date: 2016/09/02 16:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MemberListApi extends BaseApi {

    private String ids;
    private int onlyHead;

    @Override
    protected String getPath() {
        return Constants.MEMBER_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setOnlyHead(int onlyHead) {
        this.onlyHead = onlyHead;
    }
}
