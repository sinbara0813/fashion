package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/8/28.
 * Description : MonthCashedApi
 */

public class MonthCashedApi extends BaseApi {
    private Integer taxType;

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }
    @Override
    protected String getPath() {
        return Constants.BUYER_MONTH_CASHED_NUM;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
