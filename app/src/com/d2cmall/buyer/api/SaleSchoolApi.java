package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/2/2.
 * Description : SaleSchoolApi
 */

public class SaleSchoolApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_SALE_SCHOOL_TAGS_URL;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
