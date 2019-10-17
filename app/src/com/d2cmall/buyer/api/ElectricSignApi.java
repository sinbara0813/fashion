package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**工猫电签接口
 * Created by Administrator on 2018/4/25.
 * Description : ElectricSignApi
 */

public class ElectricSignApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.GONGMAO_ELECTRIC_SIGN;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
    /**
      * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 支付宝账号
     */
    /**
     * 开户银行
     */
    private String bank;//支行
    /**
     * 银行类型
     */
    private String bankType;
    /**
     * 银行卡号
     */
    private String bankSn;

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public void setBankSn(String bankSn) {
        this.bankSn = bankSn;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    //开户地区
    private String region;
}
