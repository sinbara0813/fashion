package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 直播用户列表
 * Author: hrb
 * Date: 2016/09/02 17:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveMemberListBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 849778
         * name : 13588373855
         * nickname : 关于兔兔
         * thirdPic : http://q.qlogo.cn/qqapp/1104624486/7555D30D8843676134285E3D76264E1D/40
         * headPic : /app/a/16/09/02/babd5518a4f6e92a1821cfbb29e8c2e8
         * loginCode : 13588373855
         * recommendDate :
         * recommendRebates : 10
         * sex : 女
         * designerId : 10617
         */

        private List<MemberListBean> memberList;

        public List<MemberListBean> getMemberList() {
            return memberList;
        }

        public void setMemberList(List<MemberListBean> memberList) {
            this.memberList = memberList;
        }

        public static class MemberListBean {
            private int id;
            private String name;
            private String nickname;
            private String thirdPic;
            private String headPic;
            private String loginCode;
            private String recommendDate;
            private int recommendRebates;
            private String sex;
            private int designerId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
