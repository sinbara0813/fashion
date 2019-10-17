package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 * Description : BuyerBean点击小头像查看买手请求的结果
 * /v3/api/member/info/%d
 */

public class BuyerBean extends BaseBean {

    /**
     * data : {"brands":[],"followsTotalCount":0,"fansTotalCount":0,"sharesTotalCount":0,"member":{"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"139*****850","backgroud":"","mobile":"139*****850","type":1,"recCode":"a1732a","parentId":0,"head":null,"thirdPic":null,"d2c":true,"name":"139*****850","nickname":"圣诞蔷薇","id":580667,"partnerId":0,"email":"552384293@qq.com","recId":0,"memberId":580667,"nationCode":"86"},"follow":0}
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
         * brands : []
         * followsTotalCount : 0
         * fansTotalCount : 0
         * sharesTotalCount : 0
         * member : {"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"139*****850","backgroud":"","mobile":"139*****850","type":1,"recCode":"a1732a","parentId":0,"head":null,"thirdPic":null,"d2c":true,"name":"139*****850","nickname":"圣诞蔷薇","id":580667,"partnerId":0,"email":"552384293@qq.com","recId":0,"memberId":580667,"nationCode":"86"}
         * follow : 0
         */

        private int followsTotalCount;
        private int fansTotalCount;
        private int sharesTotalCount;
        private MemberBean member;
        private int follow;
        private List<?> brands;

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

        public List<?> getBrands() {
            return brands;
        }

        public void setBrands(List<?> brands) {
            this.brands = brands;
        }

        public static class MemberBean {
            /**
             * designerId : 0
             * birthDay : null
             * distributorId : 0
             * sex : 女
             * loginCode : 139*****850
             * backgroud :
             * mobile : 139*****850
             * type : 1
             * recCode : a1732a
             * parentId : 0
             * head : null
             * thirdPic : null
             * d2c : true
             * name : 139*****850
             * nickname : 圣诞蔷薇
             * id : 580667
             * partnerId : 0
             * email : 552384293@qq.com
             * recId : 0
             * memberId : 580667
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
            private int parentId;
            private String head;
            private String thirdPic;
            private boolean d2c;
            private String name;
            private String nickname;
            private int id;
            private int partnerId;
            private String email;
            private int recId;
            private int memberId;
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

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
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

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
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
