package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2018/2/1.
 */

public class LiveCouponNewBean extends BaseBean {

    /**
     * data : {"coupon":{"enableDate":"2018/01/19 00:00","amount":500,"couponName":"测试设计师优惠券","name":"测试设计师优惠券","activeDay":20,"expireDate":"2018/03/11 00:00","remark":"伤心啊谁","id":1063,"type":"PASSWORD","isClaim":true,"needAmount":1000}}
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
         * coupon : {"enableDate":"2018/01/19 00:00","amount":500,"couponName":"测试设计师优惠券","name":"测试设计师优惠券","activeDay":20,"expireDate":"2018/03/11 00:00","remark":"伤心啊谁","id":1063,"type":"PASSWORD","isClaim":true,"needAmount":1000}
         */

        private CouponBean coupon;

        private CouponGroupBean couponGroup;

        public CouponGroupBean getCouponGroup() {
            return couponGroup;
        }

        public void setCouponGroup(CouponGroupBean couponGroup) {
            this.couponGroup = couponGroup;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public static class CouponGroupBean{
            private String couponName;
            private String name;
            private long id;
            private boolean isClaim;

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public boolean isClaim() {
                return isClaim;
            }

            public void setClaim(boolean claim) {
                isClaim = claim;
            }
        }

        public static class CouponBean {
            /**
             * enableDate : 2018/01/19 00:00
             * amount : 500
             * couponName : 测试设计师优惠券
             * name : 测试设计师优惠券
             * activeDay : 20
             * expireDate : 2018/03/11 00:00
             * remark : 伤心啊谁
             * id : 1063
             * type : PASSWORD
             * isClaim : true
             * needAmount : 1000
             */

            private String enableDate;
            private double amount;
            private String couponName;
            private String name;
            private int activeDay;
            private String expireDate;
            private String remark;
            private int id;
            private String type;
            private boolean isClaim;
            private double needAmount;

            public String getEnableDate() {
                return enableDate;
            }

            public void setEnableDate(String enableDate) {
                this.enableDate = enableDate;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getActiveDay() {
                return activeDay;
            }

            public void setActiveDay(int activeDay) {
                this.activeDay = activeDay;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isIsClaim() {
                return isClaim;
            }

            public void setIsClaim(boolean isClaim) {
                this.isClaim = isClaim;
            }

            public double getNeedAmount() {
                return needAmount;
            }

            public void setNeedAmount(double needAmount) {
                this.needAmount = needAmount;
            }
        }
    }
}
