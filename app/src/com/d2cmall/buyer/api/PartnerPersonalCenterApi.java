package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/4/19.
 * Description : PartnerPersonalCenterApi
 */

public class PartnerPersonalCenterApi extends BaseApi {
    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    private long partnerId;
    @Override
    protected String getPath() {
        return Constants.PARTNER_SUMMARY;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
