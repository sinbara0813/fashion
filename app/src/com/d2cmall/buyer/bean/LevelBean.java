package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/11 15:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LevelBean extends BaseBean {

    /**
     * data : {"childrenAmount":0,"partner":{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"匿名_3003881","loginCode":"25000000002","cashAmount":0,"id":64,"storeId":null,"headPic":"","parentId":63},"applyCashAmount":null,"member":{"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"25000000002","backgroud":"","mobile":"25000000002","type":1,"recCode":"m2ii7z","parentId":0,"head":null,"thirdPic":null,"d2c":true,"name":"25000000002","nickname":"","id":3003881,"partnerId":64,"email":null,"recId":0,"memberId":3003881,"nationCode":"86"}}
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
         * childrenAmount : 0
         * partner : {"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"匿名_3003881","loginCode":"25000000002","cashAmount":0,"id":64,"storeId":null,"headPic":"","parentId":63}
         * applyCashAmount : null
         * member : {"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"25000000002","backgroud":"","mobile":"25000000002","type":1,"recCode":"m2ii7z","parentId":0,"head":null,"thirdPic":null,"d2c":true,"name":"25000000002","nickname":"","id":3003881,"partnerId":64,"email":null,"recId":0,"memberId":3003881,"nationCode":"86"}
         */

        private int childrenAmount;
        private PartnerBean partner;
        private MemberBean member;

        public int getChildrenAmount() {
            return childrenAmount;
        }

        public void setChildrenAmount(int childrenAmount) {
            this.childrenAmount = childrenAmount;
        }

        public PartnerBean getPartner() {
            return partner;
        }

        public void setPartner(PartnerBean partner) {
            this.partner = partner;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class PartnerBean {
            /**
             * totalAmount : 0
             * totalOrderAmount : 0
             * level : 2
             * name : 匿名_3003881
             * loginCode : 25000000002
             * cashAmount : 0
             * id : 64
             * storeId : null
             * headPic :
             * parentId : 63
             */

            private int totalAmount;
            private int totalOrderAmount;
            private int level;
            private String name;
            private String loginCode;
            private int cashAmount;
            private int id;
            private Object storeId;
            private String headPic;
            private int parentId;

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public int getTotalOrderAmount() {
                return totalOrderAmount;
            }

            public void setTotalOrderAmount(int totalOrderAmount) {
                this.totalOrderAmount = totalOrderAmount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public int getCashAmount() {
                return cashAmount;
            }

            public void setCashAmount(int cashAmount) {
                this.cashAmount = cashAmount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getStoreId() {
                return storeId;
            }

            public void setStoreId(Object storeId) {
                this.storeId = storeId;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }
        }

        public static class MemberBean {
            /**
             * designerId : 0
             * birthDay : null
             * distributorId : 0
             * sex : 女
             * loginCode : 25000000002
             * backgroud :
             * mobile : 25000000002
             * type : 1
             * recCode : m2ii7z
             * parentId : 0
             * head : null
             * thirdPic : null
             * d2c : true
             * name : 25000000002
             * nickname :
             * id : 3003881
             * partnerId : 64
             * email : null
             * recId : 0
             * memberId : 3003881
             * nationCode : 86
             */

            private int designerId;
            private Object birthDay;
            private int distributorId;
            private String sex;
            private String loginCode;
            private String backgroud;
            private String mobile;
            private int type;
            private String recCode;
            private int parentId;
            private Object head;
            private Object thirdPic;
            private boolean d2c;
            private String name;
            private String nickname;
            private int id;
            private int partnerId;
            private Object email;
            private int recId;
            private int memberId;
            private String nationCode;

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public Object getBirthDay() {
                return birthDay;
            }

            public void setBirthDay(Object birthDay) {
                this.birthDay = birthDay;
            }

            public int getDistributorId() {
                return distributorId;
            }

            public void setDistributorId(int distributorId) {
                this.distributorId = distributorId;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public String getBackgroud() {
                return backgroud;
            }

            public void setBackgroud(String backgroud) {
                this.backgroud = backgroud;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRecCode() {
                return recCode;
            }

            public void setRecCode(String recCode) {
                this.recCode = recCode;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public Object getHead() {
                return head;
            }

            public void setHead(Object head) {
                this.head = head;
            }

            public Object getThirdPic() {
                return thirdPic;
            }

            public void setThirdPic(Object thirdPic) {
                this.thirdPic = thirdPic;
            }

            public boolean isD2c() {
                return d2c;
            }

            public void setD2c(boolean d2c) {
                this.d2c = d2c;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
                this.partnerId = partnerId;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getNationCode() {
                return nationCode;
            }

            public void setNationCode(String nationCode) {
                this.nationCode = nationCode;
            }
        }
    }
}
