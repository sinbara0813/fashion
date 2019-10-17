package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/28 15:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveCouponBean extends BaseBean {

    /**
     * data : {"coupon":{"id":634,"createDate":1493346416000,"creator":"langhuiqin","modifyDate":1493357000000,"lastModifyMan":"langhuiqin","code":"80803be8e4274c75bcc619826f62a591","cipher":"80803be8e4274c75bcc619826f62a591","type":"CASH","managerStatus":"SENDED","name":"设计师包含（包含）","subTitle":null,"enableDate":1493481600000,"expireDate":1493568000000,"activeDay":0,"activeHour":0,"claimStart":1493308800000,"claimEnd":1493481600000,"amount":80,"needAmount":100,"quantity":10,"claimed":7,"claimLimit":10,"checkAssociation":"DESIGNER","remark":"满","pic":null,"exclude":false,"enable":1,"random":false,"wxCardId":null,"redirectUrl":null,"transfer":false,"pcCode":null,"wapCode":null,"over":false},"type":0}
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
         * coupon : {"id":634,"createDate":1493346416000,"creator":"langhuiqin","modifyDate":1493357000000,"lastModifyMan":"langhuiqin","code":"80803be8e4274c75bcc619826f62a591","cipher":"80803be8e4274c75bcc619826f62a591","type":"CASH","managerStatus":"SENDED","name":"设计师包含（包含）","subTitle":null,"enableDate":1493481600000,"expireDate":1493568000000,"activeDay":0,"activeHour":0,"claimStart":1493308800000,"claimEnd":1493481600000,"amount":80,"needAmount":100,"quantity":10,"claimed":7,"claimLimit":10,"checkAssociation":"DESIGNER","remark":"满","pic":null,"exclude":false,"enable":1,"random":false,"wxCardId":null,"redirectUrl":null,"transfer":false,"pcCode":null,"wapCode":null,"over":false}
         * type : 0
         */

        private LiveInBean.DataBean.CouponBean coupon;
        private int type;

        public LiveInBean.DataBean.CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(LiveInBean.DataBean.CouponBean coupon) {
            this.coupon = coupon;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }
}
