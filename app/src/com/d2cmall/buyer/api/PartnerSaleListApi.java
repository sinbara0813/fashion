package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/12 13:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerSaleListApi extends SimpleApi {

    public void setP(int p) {
        this.pageNumber = p;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize;
    private int pageNumber;

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setMasterid(String masterid) {
        this.masterId = masterid;
    }

    private String partnerId;
    private String parentId;
    private String masterId;

    private String superId;

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_BILL_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
