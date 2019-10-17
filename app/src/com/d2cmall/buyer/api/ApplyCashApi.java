package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/1/20.
 * Description : ApplyCashApi
 */

public class ApplyCashApi extends BaseApi {
    public double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    private String payType;
    private double applyAmount;
    @Override
    protected String getPath() {
        return Constants.POST_PARTNER_WITHDRAW_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
