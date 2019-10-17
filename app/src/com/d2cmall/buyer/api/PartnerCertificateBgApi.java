package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/9/25.
 * Description : PartnerCertificateBgApi
 */

public class PartnerCertificateBgApi extends BaseApi {
    private long partnerId;
    private String appId;
    private String content;
    private String page;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPage(String page) {
        this.page = page;
    }
    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }
    @Override
    protected String getPath() {
        return Constants.BUYER_CERTIFICATE;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
