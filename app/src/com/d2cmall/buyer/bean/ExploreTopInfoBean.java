package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

/**
 * 个人主页顶部信息
 * Author: Blend
 * Date: 2016/08/18 17:42
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class ExploreTopInfoBean extends BaseBean {

    /**
     * followsTotalCount : 2
     * fansTotalCount : 173
     * sharesTotalCount : 17
     * member : {"id":24530,"memberId":24530,"name":"18651811111","mobile":"18651811111","nickname":"徐菲阳","sex":"女","d2c":true,"thirdPic":"","head":"/app/a/16/04/08/bf715fe552a79db623c1135136456ca1","backgroud":"","recCode":"8e4f9a","recId":325,"partnerId":"","bindPartnerId":"","designerId":0,"loginCode":"18651811111"}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int followsTotalCount;
        private int fansTotalCount;
        private int sharesTotalCount;
        private int attentionsCount;
        private double giveAmount;
        private double presentAmount;
        private int follow;
        /**
         * id : 24530
         * memberId : 24530
         * name : 18651811111
         * mobile : 18651811111
         * nickname : 徐菲阳
         * sex : 女
         * d2c : true
         * thirdPic :
         * head : /app/a/16/04/08/bf715fe552a79db623c1135136456ca1
         * backgroud :
         * recCode : 8e4f9a
         * recId : 325
         * partnerId :
         * bindPartnerId :
         * designerId : 0
         * loginCode : 18651811111
         */

        private MemberEntity member;

        public double getGiveAmount() {
            return giveAmount;
        }

        public void setGiveAmount(double giveAmount) {
            this.giveAmount = giveAmount;
        }

        public int getAttentionsCount() {
            return attentionsCount;
        }

        public void setAttentionsCount(int attentionsCount) {
            this.attentionsCount = attentionsCount;
        }

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

        public double getPresentAmount() {
            return presentAmount;
        }

        public void setPresentAmount(double presentAmount) {
            this.presentAmount = presentAmount;
        }

        public MemberEntity getMember() {
            return member;
        }

        public void setMember(MemberEntity member) {
            this.member = member;
        }

        public static class MemberEntity {
            private int id;
            private long memberId;
            private String name;
            private String mobile;
            private String nickname;
            private String sex;
            private boolean d2c;
            private String thirdPic;
            private String head;
            private String backgroud;
            private String recCode;
            private int recId;
            private String partnerId;
            private String bindPartnerId;
            private long designerId;
            private int type;
            private String designerName;
            private String brandName;
            private int follow;

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getMemberId() {
                return memberId;
            }

            public void setMemberId(long memberId) {
                this.memberId = memberId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return !Util.isEmpty(nickname) ? nickname : "匿名_" + memberId;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public boolean isD2c() {
                return d2c;
            }

            public void setD2c(boolean d2c) {
                this.d2c = d2c;
            }

            public String getThirdPic() {
                return thirdPic;
            }

            public void setThirdPic(String thirdPic) {
                this.thirdPic = thirdPic;
            }

            public String getHead() {
                return !Util.isEmpty(head) ? head : thirdPic;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getBackgroud() {
                return backgroud;
            }

            public void setBackgroud(String backgroud) {
                this.backgroud = backgroud;
            }

            public String getRecCode() {
                return recCode;
            }

            public void setRecCode(String recCode) {
                this.recCode = recCode;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public String getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
            }

            public String getBindPartnerId() {
                return bindPartnerId;
            }

            public void setBindPartnerId(String bindPartnerId) {
                this.bindPartnerId = bindPartnerId;
            }

            public long getDesignerId() {
                return designerId;
            }

            public void setDesignerId(long designerId) {
                this.designerId = designerId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getDesignerName() {
                return designerName;
            }

            public void setDesignerName(String designerName) {
                this.designerName = designerName;
            }

        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
