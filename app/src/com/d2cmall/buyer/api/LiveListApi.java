package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class LiveListApi extends BaseApi {

    private Integer p;
    private Integer pageSize;
    private Long memberId;

    @Override
    protected String getPath() {
        return Constants.LIVE_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
