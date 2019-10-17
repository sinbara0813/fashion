package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/1/11.
 * Description : PartnerInfoBean
 */

public class PartnerInfoBean extends BaseBean {

    /**
     * data : {"childrenAmount":0,"partner":{"totalAmount":499.75,"totalOrderAmount":1999,"level":2,"name":"雪🌸","loginCode":"13588373855","cashAmount":120,"id":41,"storeId":40,"headPic":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","parentId":57},"applyCashAmount":31,"member":{"designerId":0,"birthDay":"0052/10/10 00:00:00","distributorId":0,"sex":"女","loginCode":"13588373855","backgroud":"/app/a/17/12/21/DAEF74F039CAFE02EE7690C8A9EE953A","mobile":"135883373855","type":3,"recCode":"aaacea","parentId":57,"head":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","thirdPic":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","d2c":true,"name":"13588373855","nickname":"雪🌸","id":849778,"partnerId":41,"email":"1225860007@qq.com","recId":0,"memberId":849778,"nationCode":"86"}}
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
         * partner : {"totalAmount":499.75,"totalOrderAmount":1999,"level":2,"name":"雪🌸","loginCode":"13588373855","cashAmount":120,"id":41,"storeId":40,"headPic":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","parentId":57}
         * applyCashAmount : 31.0
         * member : {"designerId":0,"birthDay":"0052/10/10 00:00:00","distributorId":0,"sex":"女","loginCode":"13588373855","backgroud":"/app/a/17/12/21/DAEF74F039CAFE02EE7690C8A9EE953A","mobile":"135883373855","type":3,"recCode":"aaacea","parentId":57,"head":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","thirdPic":"/app/a/17/12/07/89655D47DD761AD179710C230C1DBA10","d2c":true,"name":"13588373855","nickname":"雪🌸","id":849778,"partnerId":41,"email":"1225860007@qq.com","recId":0,"memberId":849778,"nationCode":"86"}
         */

        private double childrenAmount;
        private PartnerBean partner;
        private double applyCashAmount;
        private MemberBean member;

        public double getChildrenAmount() {
            return childrenAmount;
        }

        public void setChildrenAmount(double childrenAmount) {
            this.childrenAmount = childrenAmount;
        }

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

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class PartnerBean {
            /**
             * birthday :
             * occupation :
             * loginCode : 13588373855
             * cashAmount : 250.0
             * experience :
             * matrimony :
             * bank :
             * id : 41
             * memberId : 849778
             * weixinCount :
             * alipay :
             * address :
             * level : 1
             * bankType :
             * weiboCount :
             * consumption :
             * identityCard :
             * storeId : 40
             * headPic : /app/a/18/02/01/7B4768115F261FDC2C89445AB7045385
             * parentId : null
             * totalAmount : 1034.16
             * realName :
             * bankSn :
             * totalOrderAmount : 7229.01
             * name : 雪��
             */

            private String birthday;
            private String occupation;
            private String loginCode;
            private double cashAmount;
            private String experience;
            private String matrimony;
            private String bank;
            private long id;
            private long memberId;
            private String weixinCount;
            private String alipay;
            private String address;
            private int level;
            private String bankType;
            private String weiboCount;
            private String consumption;
            private String identityCard;
            private long storeId;
            private String headPic;
            private long parentId;
            private double totalAmount;
            private String realName;
            private String bankSn;
            private double totalOrderAmount;
            private String name;

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getLicenseNum() {
                return licenseNum;
            }

            public void setLicenseNum(String licenseNum) {
                this.licenseNum = licenseNum;
            }

            public String getLicense() {
                return license;
            }

            public void setLicense(String license) {
                this.license = license;
            }

            private String company;
            private String licenseNum;
            private String license;
            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            private String region;


            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getOccupation() {
                return occupation;
            }

            public void setOccupation(String occupation) {
                this.occupation = occupation;
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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public long getStoreId() {
                return storeId;
            }

            public void setStoreId(long storeId) {
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MemberBean {
            /**
             * designerId : 0
             * birthDay : 0052/10/10 00:00:00
             * distributorId : 0
             * sex : 女
             * loginCode : 13588373855
             * backgroud : /app/a/17/12/21/DAEF74F039CAFE02EE7690C8A9EE953A
             * mobile : 135883373855
             * type : 3
             * recCode : aaacea
             * parentId : 57
             * head : /app/a/17/12/07/89655D47DD761AD179710C230C1DBA10
             * thirdPic : /app/a/17/12/07/89655D47DD761AD179710C230C1DBA10
             * d2c : true
             * name : 13588373855
             * nickname : 雪🌸
             * id : 849778
             * partnerId : 41
             * email : 1225860007@qq.com
             * recId : 0
             * memberId : 849778
             * nationCode : 86
             */

            private int designerId;
            private String birthDay;
            private int distributorId;
            private String sex;
            private String loginCode;
            private String backgroud;
            private String mobile;
            private int type;
            private String recCode;
            private long parentId;
            private String head;
            private String thirdPic;
            private boolean d2c;
            private String name;
            private String nickname;
            private int id;
            private long partnerId;
            private String email;
            private int recId;
            private long memberId;
            private String nationCode;

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public String getBirthDay() {
                return birthDay;
            }

            public void setBirthDay(String birthDay) {
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

            public long getParentId() {
                return parentId;
            }

            public void setParentId(long parentId) {
                this.parentId = parentId;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getThirdPic() {
                return thirdPic;
            }

            public void setThirdPic(String thirdPic) {
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

            public long getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(long partnerId) {
                this.partnerId = partnerId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public long getMemberId() {
                return memberId;
            }

            public void setMemberId(long memberId) {
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
