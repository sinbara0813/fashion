package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/17 14:28
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartnerSummaryBean extends BaseBean {

    /**
     * data : {"partner":{"birthday":"","partnerOpenId":null,"occupation":"","expiredDate":null,"loginCode":"990*****001","cashAmount":0,"experience":"","applyAmount":0,"matrimony":"","bank":"","company":"","id":4145,"createDate":"2018/04/17 11:33:32","memberId":3031125,"weixinCount":"","alipay":"","address":"","level":0,"bankType":"","weiboCount":"","consumption":"","licenseNum":"","identityCard":"","storeId":4142,"headPic":"","parentId":null,"counselorId":null,"masterId":null,"totalAmount":1418.76,"realName":"","license":"","bankSn":"","totalOrderAmount":52927,"weixin":"","name":"匿名_3031125","region":"","status":1},"incomeData":[{"amount":509.6,"type":"BILL"},{"amount":1.98,"type":"DIFF"},{"amount":911.14,"type":"TEAM"}]}
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
         * partner : {"birthday":"","partnerOpenId":null,"occupation":"","expiredDate":null,"loginCode":"990*****001","cashAmount":0,"experience":"","applyAmount":0,"matrimony":"","bank":"","company":"","id":4145,"createDate":"2018/04/17 11:33:32","memberId":3031125,"weixinCount":"","alipay":"","address":"","level":0,"bankType":"","weiboCount":"","consumption":"","licenseNum":"","identityCard":"","storeId":4142,"headPic":"","parentId":null,"counselorId":null,"masterId":null,"totalAmount":1418.76,"realName":"","license":"","bankSn":"","totalOrderAmount":52927,"weixin":"","name":"匿名_3031125","region":"","status":1}
         * incomeData : [{"amount":509.6,"type":"BILL"},{"amount":1.98,"type":"DIFF"},{"amount":911.14,"type":"TEAM"}]
         */

        private PartnerBean partner;
        private List<IncomeDataBean> incomeData;

        public PartnerBean getPartner() {
            return partner;
        }

        public void setPartner(PartnerBean partner) {
            this.partner = partner;
        }

        public List<IncomeDataBean> getIncomeData() {
            return incomeData;
        }

        public void setIncomeData(List<IncomeDataBean> incomeData) {
            this.incomeData = incomeData;
        }

        public static class PartnerBean {
            /**
             * birthday :
             * partnerOpenId : null
             * occupation :
             * expiredDate : null
             * loginCode : 990*****001
             * cashAmount : 0.0
             * experience :
             * applyAmount : 0.0
             * matrimony :
             * bank :
             * company :
             * id : 4145
             * createDate : 2018/04/17 11:33:32
             * memberId : 3031125
             * weixinCount :
             * alipay :
             * address :
             * level : 0
             * bankType :
             * weiboCount :
             * consumption :
             * licenseNum :
             * identityCard :
             * storeId : 4142
             * headPic :
             * parentId : null
             * counselorId : null
             * masterId : null
             * totalAmount : 1418.76
             * realName :
             * license :
             * bankSn :
             * totalOrderAmount : 52927.0
             * weixin :
             * name : 匿名_3031125
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
            private Date createDate;
            private int memberId;
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
            private String parentId;
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

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getPartnerOpenId() {
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

            public Date getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Date createDate) {
                this.createDate = createDate;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
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

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
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

        public static class IncomeDataBean {
            /**
             * amount : 509.6
             * type : BILL
             */

            private double amount;
            private String type;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
