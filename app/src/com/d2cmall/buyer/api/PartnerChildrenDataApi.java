package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/4/20.
 * Description : PartnerChildrenDataApi
 */

public class PartnerChildrenDataApi extends BaseApi{
    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    private long partnerId;
    @Override
    protected String getPath() {
        return Constants.BUYER_CHILDREN_DATA;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
