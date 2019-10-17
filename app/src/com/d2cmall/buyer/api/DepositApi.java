package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class DepositApi extends BaseApi {

    private String sn;//D2C卡号码
    private String password;//D2C卡密码
    private String paySn;//支付流水号
    private String payAccount;//支付账号
    private String payChannel;//支付渠道
    private double rechargeAmount;//充值金额

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    @Override
    protected String getPath() {
        return Constants.DEPOSIT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
