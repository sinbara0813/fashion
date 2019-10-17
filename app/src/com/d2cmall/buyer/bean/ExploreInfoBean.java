package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 朋友圈主页Bean类
 * Author: Blend
 * Date: 16/4/23 16:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExploreInfoBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ShareBean.DataEntity.MemberSharesEntity memberShares;
        /**
         * id : 870010
         * memberId : 870010
         * name : 11111222233
         * mobile : 11111222233
         * email : 123456@qq.com
         * birthDay : 1993/03/06 00:00:00
         * nickname : 宁宝宝
         * sex : 女
         * d2c : true
         * thirdPic : http://tva1.sinaimg.cn/crop.0.0.720.720.1024/c59f6896jw8etednffj0aj20k00k1tcm.jpg
         * head : /app/a/16/04/22/b42bc50d5e22ca8c7d707a47e053d0d0
         * backgroud : /app/a/16/04/23/80da6007879970fccbb5624f2fe7af33
         */

        private MemberEntity member;

        public ShareBean.DataEntity.MemberSharesEntity getMemberShares() {
            return memberShares;
        }

        public void setMemberShares(ShareBean.DataEntity.MemberSharesEntity memberShares) {
            this.memberShares = memberShares;
        }

        public MemberEntity getMember() {
            return member;
        }

        public void setMember(MemberEntity member) {
            this.member = member;
        }

        public static class MemberEntity {
            private long id;
            private long memberId;
            private String name;
            private String mobile;
            private String email;
            private String birthDay;
            private String nickname;
            private String sex;
            private boolean d2c;
            private String thirdPic;
            private String head;
            private String backgroud;
            private String recCode;

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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getBirthDay() {
                return birthDay;
            }

            public void setBirthDay(String birthDay) {
                this.birthDay = birthDay;
            }

            public String getNickname() {
                return nickname;
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
                return head;
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
        }
    }
}
