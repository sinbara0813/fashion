package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 直播头像列表的会员信息
 * Author: Blend
 * Date: 2016/08/25 18:06
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class LiveMembersBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 845334
         * name : 13157105526
         * nickname : Cariors
         * thirdPic :
         * headPic : /member_head/34/845334/f6ff157f91f8491d8d215524e024988f.png
         * loginCode : 13157105526
         * recommendDate :
         * recommendRebates : 10
         * sex : 女
         * designerId : 10542
         */

        private List<MemberListBean> memberList;

        public List<MemberListBean> getMemberList() {
            return memberList;
        }

        public void setMemberList(List<MemberListBean> memberList) {
            this.memberList = memberList;
        }

        public static class MemberListBean {
            private long id;
            private String name;
            private String nickname;
            private String thirdPic;
            private String headPic;
            private String loginCode;
            private String recommendDate;
            private int recommendRebates;
            private String sex;
            private int designerId;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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

            public String getThirdPic() {
                return thirdPic;
            }

            public void setThirdPic(String thirdPic) {
                this.thirdPic = thirdPic;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public String getRecommendDate() {
                return recommendDate;
            }

            public void setRecommendDate(String recommendDate) {
                this.recommendDate = recommendDate;
            }

            public int getRecommendRebates() {
                return recommendRebates;
            }

            public void setRecommendRebates(int recommendRebates) {
                this.recommendRebates = recommendRebates;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }
        }
    }
}
