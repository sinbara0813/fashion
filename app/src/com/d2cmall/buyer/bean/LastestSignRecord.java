package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/11.
 * Description : LastestSignRecord
 */

public class LastestSignRecord extends BaseBean {

    /**
     * data : {"memberDailySign":{"reward":1,"totalReward":8,"signDate":1534323127000,"memberId":3029893,"totalDay":1}}
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
         * memberDailySign : {"reward":1,"totalReward":8,"signDate":1534323127000,"memberId":3029893,"totalDay":1}
         */

        private MemberDailySignBean memberDailySign;

        public MemberDailySignBean getMemberDailySign() {
            return memberDailySign;
        }

        public void setMemberDailySign(MemberDailySignBean memberDailySign) {
            this.memberDailySign = memberDailySign;
        }

        public static class MemberDailySignBean {
            /**
             * reward : 1
             * totalReward : 8
             * signDate : 1534323127000
             * memberId : 3029893
             * totalDay : 1
             */

            private int reward;
            private int totalReward;
            private Date signDate;
            private int memberId;
            private int totalDay;

            public int getReward() {
                return reward;
            }

            public void setReward(int reward) {
                this.reward = reward;
            }

            public int getTotalReward() {
                return totalReward;
            }

            public void setTotalReward(int totalReward) {
                this.totalReward = totalReward;
            }

            public Date getSignDate() {
                return signDate;
            }

            public void setSignDate(Date signDate) {
                this.signDate = signDate;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getTotalDay() {
                return totalDay;
            }

            public void setTotalDay(int totalDay) {
                this.totalDay = totalDay;
            }
        }
    }
}
