package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonInfoBean extends BaseBean {

    /**
     * data : {"brands":[{"salesProductCount":72,"name":"CHI ZHANG","recommend":0,"id":10015,"designer":"张驰","slogan":"坚持设计的原创性","headPic":"/dh/15/10015/8437b90c690985a06aa0005c338a0ee7"}],"followsTotalCount":0,"fansTotalCount":0,"sharesTotalCount":4354,"member":{"designerId":10015,"birthDay":"1970/01/01 00:00:00","brandName":"张驰","bindPartnerId":0,"sex":"女","loginCode":"13164212022","backgroud":"","mobile":"13164212022","allowPartner":0,"type":2,"recCode":"vn2uq3","designerName":"CHI ZHANG","head":null,"thirdPic":"","d2c":true,"name":"13164212022","nickname":"驰张国际","id":2794536,"partnerId":0,"recId":0,"email":"info@zhang-chi.com","memberId":2794536,"nationCode":"86"},"follow":0}
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
         * brands : [{"salesProductCount":72,"name":"CHI ZHANG","recommend":0,"id":10015,"designer":"张驰","slogan":"坚持设计的原创性","headPic":"/dh/15/10015/8437b90c690985a06aa0005c338a0ee7"}]
         * followsTotalCount : 0
         * fansTotalCount : 0
         * sharesTotalCount : 4354
         * member : {"designerId":10015,"birthDay":"1970/01/01 00:00:00","brandName":"张驰","bindPartnerId":0,"sex":"女","loginCode":"13164212022","backgroud":"","mobile":"13164212022","allowPartner":0,"type":2,"recCode":"vn2uq3","designerName":"CHI ZHANG","head":null,"thirdPic":"","d2c":true,"name":"13164212022","nickname":"驰张国际","id":2794536,"partnerId":0,"recId":0,"email":"info@zhang-chi.com","memberId":2794536,"nationCode":"86"}
         * follow : 0
         */

        private int followsTotalCount;
        private int fansTotalCount;
        private int sharesTotalCount;
        private MemberBean member;
        private int follow;
        private List<BrandsBean> brands;

        public int getFollowsTotalCount() {
            return followsTotalCount;
        }

        public void setFollowsTotalCount(int followsTotalCount) {
            this.followsTotalCount = followsTotalCount;
        }

        public int getFansTotalCount() {
            return fansTotalCount;
        }

        public void setFansTotalCount(int fansTotalCount) {
            this.fansTotalCount = fansTotalCount;
        }

        public int getSharesTotalCount() {
            return sharesTotalCount;
        }

        public void setSharesTotalCount(int sharesTotalCount) {
            this.sharesTotalCount = sharesTotalCount;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public static class MemberBean {
            /**
             * designerId : 10015
             * birthDay : 1970/01/01 00:00:00
             * brandName : 张驰
             * bindPartnerId : 0
             * sex : 女
             * loginCode : 13164212022
             * backgroud :
             * mobile : 13164212022
             * allowPartner : 0
             * type : 2
             * recCode : vn2uq3
             * designerName : CHI ZHANG
             * head : null
             * thirdPic :
             * d2c : true
             * name : 13164212022
             * nickname : 驰张国际
             * id : 2794536
             * partnerId : 0
             * recId : 0
             * email : info@zhang-chi.com
             * memberId : 2794536
             * nationCode : 86
             */

            private int designerId;
            private String birthDay;
            private String brandName;
            private int bindPartnerId;
            private String sex;
            private String loginCode;
            private String backgroud;
            private String mobile;
            private int allowPartner;
            private int type;
            private String recCode;
            private String designerName;
            private String head;
            private String thirdPic;
            private boolean d2c;
            private String name;
            private String nickname;
            private int id;
            private int partnerId;
            private int recId;
            private String email;
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

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public int getBindPartnerId() {
                return bindPartnerId;
            }

            public void setBindPartnerId(int bindPartnerId) {
                this.bindPartnerId = bindPartnerId;
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

            public int getAllowPartner() {
                return allowPartner;
            }

            public void setAllowPartner(int allowPartner) {
                this.allowPartner = allowPartner;
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

            public String getDesignerName() {
                return designerName;
            }

            public void setDesignerName(String designerName) {
                this.designerName = designerName;
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

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
                this.partnerId = partnerId;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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

        public static class BrandsBean {
            /**
             * salesProductCount : 72
             * name : CHI ZHANG
             * recommend : 0
             * id : 10015
             * designer : 张驰
             * slogan : 坚持设计的原创性
             * headPic : /dh/15/10015/8437b90c690985a06aa0005c338a0ee7
             */

            private int salesProductCount;
            private String name;
            private int recommend;
            private int id;
            private String designer;
            private String slogan;
            private String headPic;

            public int getSalesProductCount() {
                return salesProductCount;
            }

            public void setSalesProductCount(int salesProductCount) {
                this.salesProductCount = salesProductCount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDesigner() {
                return designer;
            }

            public void setDesigner(String designer) {
                this.designer = designer;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }
        }
    }
}
