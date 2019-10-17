package com.d2cmall.buyer.api;


/**
 * 可能要传memberId的Bean类
 * Author: Blend
 * Date: 16/5/19 9:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MemberIdApi extends BaseApi {

    private Long memberId;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer status;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setP(Integer p) {
        this.pageNumber = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
