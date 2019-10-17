package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/11 19:25
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FansApi extends BaseApi {
    private Integer pageSize;
    private Integer p;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    private Long memberId;
    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @Override
    protected String getPath() {
        return Constants.MY_FANS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
