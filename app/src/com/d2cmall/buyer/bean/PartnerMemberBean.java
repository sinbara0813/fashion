package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/13 15:39
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartnerMemberBean extends BaseBean {

    /**
     * data : {"partner":{"birthday":"","partnerOpenId":null,"occupation":"","expiredDate":null,"loginCode":"189*****111","cashAmount":0,"experience":"","applyAmount":0,"matrimony":"","bank":"","company":"","id":222,"createDate":"2018/02/24 12:58:35","memberId":2397,"weixinCount":"","alipay":"","address":"","level":2,"bankType":"","weiboCount":"","consumption":"","licenseNum":"","identityCard":"","storeId":221,"headPic":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","parentId":90,"counselorId":null,"masterId":null,"totalAmount":0,"realName":"","license":"","bankSn":"","totalOrderAmount":0,"weixin":"","name":"咯JOJO","region":"","status":1},"applyCashAmount":0,"member":{"designerId":120,"birthDay":null,"distributorId":0,"sex":"男","loginCode":"189*****111","backgroud":"","mobile":"189*****111","type":2,"recCode":"8d7dcc","parentId":0,"head":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","thirdPic":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","d2c":true,"name":"189*****111","nickname":"咯JOJO","id":2397,"partnerId":222,"email":null,"recId":0,"memberId":2397,"nationCode":"86"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * partner : {"birthday":"","partnerOpenId":null,"occupation":"","expiredDate":null,"loginCode":"189*****111","cashAmount":0,"experience":"","applyAmount":0,"matrimony":"","bank":"","company":"","id":222,"createDate":"2018/02/24 12:58:35","memberId":2397,"weixinCount":"","alipay":"","address":"","level":2,"bankType":"","weiboCount":"","consumption":"","licenseNum":"","identityCard":"","storeId":221,"headPic":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","parentId":90,"counselorId":null,"masterId":null,"totalAmount":0,"realName":"","license":"","bankSn":"","totalOrderAmount":0,"weixin":"","name":"咯JOJO","region":"","status":1}
         * applyCashAmount : 0
         * member : {"designerId":120,"birthDay":null,"distributorId":0,"sex":"男","loginCode":"189*****111","backgroud":"","mobile":"189*****111","type":2,"recCode":"8d7dcc","parentId":0,"head":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","thirdPic":"/app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3","d2c":true,"name":"189*****111","nickname":"咯JOJO","id":2397,"partnerId":222,"email":null,"recId":0,"memberId":2397,"nationCode":"86"}
         */

        private PartnerBean partner;
        private double applyCashAmount;

        public PartnerBean getPartner() {
            return partner;
        }

        public void setPartner(PartnerBean partner) {
            this.partner = partner;
        }

        public double getApplyCashAmount() {
            return applyCashAmount;
        }

        public void setApplyCashAmount(double applyCashAmount) {
            this.applyCashAmount = applyCashAmount;
        }

        public static class PartnerBean {
            /**
             * birthday :
             * partnerOpenId : null
             * occupation :
             * expiredDate : null
             * loginCode : 189*****111
             * cashAmount : 0
             * experience :
             * applyAmount : 0
             * matrimony :
             * bank :
             * company :
             * id : 222
             * createDate : 2018/02/24 12:58:35
             * memberId : 2397
             * weixinCount :
             * alipay :
             * address :
             * level : 2
             * bankType :
             * weiboCount :
             * consumption :
             * licenseNum :
             * identityCard :
             * storeId : 221
             * headPic : /app/a/18/01/02/f64da6dc02cfc0d6dbd6f5edec8c87e3
             * parentId : 90
             * counselorId : null
             * masterId : null
             * totalAmount : 0
             * realName :
             * license :
             * bankSn :
             * totalOrderAmount : 0
             * weixin :
             * name : 咯JOJO
             * region :
             * status : 1
             */

            private String birthday;
            private String partnerOpenId;
            private String occupation;
            private String expiredDate;
            private String loginCode;
            private double cashAmount;
            private String experience;
            private double applyAmount;
            private String matrimony;
            private String bank;
            private String company;
            private long id;
            private String createDate;
            private long memberId;
            private String weixinCount;
            private String alipay;
            private String address;
            private int level;
            private String bankType;
            private String weiboCount;
            private String consumption;
            private String licenseNum;
            private String identityCard;
            private int storeId;
            private String headPic;
            private long parentId;
            private String counselorId;
            private String masterId;
            private double totalAmount;
            private String realName;
            private String license;
            private String bankSn;
            private double totalOrderAmount;
            private String weixin;
            private String name;
            private String region;
            @SerializedName("status")
            private int statusX;

            private int contract;//工猫是否电签,0否,1是

            private int pointCount;//分销当前积分

            public int getPointCount() {
                return pointCount;
            }

            public void setPointCount(int pointCount) {
                this.pointCount = pointCount;
            }

            public int getContract() {
                return contract;
            }

            public void setContract(int contract) {
                this.contract = contract;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public Object getPartnerOpenId() {
                return partnerOpenId;
            }

            public void setPartnerOpenId(String partnerOpenId) {
                this.partnerOpenId = partnerOpenId;
            }

            public String getOccupation() {
                return occupation;
            }

            public void setOccupation(String occupation) {
                this.occupation = occupation;
            }

            public String getExpiredDate() {
                return expiredDate;
            }

            public void setExpiredDate(String expiredDate) {
                this.expiredDate = expiredDate;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public double getCashAmount() {
                return cashAmount;
            }

            public void setCashAmount(double cashAmount) {
                this.cashAmount = cashAmount;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public double getApplyAmount() {
                return applyAmount;
            }

            public void setApplyAmount(double applyAmount) {
                this.applyAmount = applyAmount;
            }

            public String getMatrimony() {
                return matrimony;
            }

            public void setMatrimony(String matrimony) {
                this.matrimony = matrimony;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public long getMemberId() {
                return memberId;
            }

            public void setMemberId(long memberId) {
                this.memberId = memberId;
            }

            public String getWeixinCount() {
                return weixinCount;
            }

            public void setWeixinCount(String weixinCount) {
                this.weixinCount = weixinCount;
            }

            public String getAlipay() {
                return alipay;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getBankType() {
                return bankType;
            }

            public void setBankType(String bankType) {
                this.bankType = bankType;
            }

            public String getWeiboCount() {
                return weiboCount;
            }

            public void setWeiboCount(String weiboCount) {
                this.weiboCount = weiboCount;
            }

            public String getConsumption() {
                return consumption;
            }

            public void setConsumption(String consumption) {
                this.consumption = consumption;
            }

            public String getLicenseNum() {
                return licenseNum;
            }

            public void setLicenseNum(String licenseNum) {
                this.licenseNum = licenseNum;
            }

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public long getParentId() {
                return parentId;
            }

            public void setParentId(long parentId) {
                this.parentId = parentId;
            }

            public String getCounselorId() {
                return counselorId;
            }

            public void setCounselorId(String counselorId) {
                this.counselorId = counselorId;
            }

            public String getMasterId() {
                return masterId;
            }

            public void setMasterId(String masterId) {
                this.masterId = masterId;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getLicense() {
                return license;
            }

            public void setLicense(String license) {
                this.license = license;
            }

            public String getBankSn() {
                return bankSn;
            }

            public void setBankSn(String bankSn) {
                this.bankSn = bankSn;
            }

            public double getTotalOrderAmount() {
                return totalOrderAmount;
            }

            public void setTotalOrderAmount(double totalOrderAmount) {
                this.totalOrderAmount = totalOrderAmount;
            }

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }
        }
    }
}
