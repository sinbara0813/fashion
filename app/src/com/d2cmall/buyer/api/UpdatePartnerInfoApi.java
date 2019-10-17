package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/2/1.
 * Description : UpdatePartnerInfoApi
 */

public class UpdatePartnerInfoApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_INFO_UPDATA_URL;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setWeixinCount(String weixinCount) {
        this.weixinCount = weixinCount;
    }

    public void setWeiboCount(String weiboCount) {
        this.weiboCount = weiboCount;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
    private String address;
	 private String occupation;
	 private String experience;
	 private String birthday;
	 private String weixinCount;
	 private String weiboCount;
	 private String consumption;
    private String company;
    private String licenseNum;

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    private String license;
    private String region;

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String headPic;

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

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public void setRegion(String region) {//开户行的省市
        this.region = region;
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
    private String alipay;
    public void setId(long id) {
        this.id = id;
    }

    private long id;

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
