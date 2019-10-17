package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2018/6/12.
 */

public class BuyCouponBean extends BaseBean {

    /**
     * data : {"couponOrder":{"totalAmount":0.01,"couponName":"付费绑定设计师现金券","quantity":1,"orderSn":"CO1528787767541","payParams":"ALIPAY,WXAPPPAY,WALLET","id":7}}
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
         * couponOrder : {"totalAmount":0.01,"couponName":"付费绑定设计师现金券","quantity":1,"orderSn":"CO1528787767541","payParams":"ALIPAY,WXAPPPAY,WALLET","id":7}
         */

        private CouponOrderBean couponOrder;

        public CouponOrderBean getCouponOrder() {
            return couponOrder;
        }

        public void setCouponOrder(CouponOrderBean couponOrder) {
            this.couponOrder = couponOrder;
        }

        public static class CouponOrderBean {
            /**
             * totalAmount : 0.01
             * couponName : 付费绑定设计师现金券
             * quantity : 1
             * orderSn : CO1528787767541
             * payParams : ALIPAY,WXAPPPAY,WALLET
             * id : 7
             */

            private double totalAmount;
            private String couponName;
            private int quantity;
            private String orderSn;
            private String payParams;
            private int id;

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getPayParams() {
                return payParams;
            }

            public void setPayParams(String payParams) {
                this.payParams = payParams;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
