package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 订单数目Bean类
 * Author: Blend
 * Date: 2016/06/14 12:09
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class OrderCountBean extends BaseBean {

    /**
     * data : {"waitForPay":0,"collectionCount":3,"waitForDelivered":5,"delivered":0,"waitForComment":0,"attentionCount":0}
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
         * waitForPay : 0
         * collectionCount : 3
         * waitForDelivered : 5
         * delivered : 0
         * waitForComment : 0
         * attentionCount : 0
         */

        private int waitForPay;
        private int collectionCount;
        private int waitForDelivered;
        private int delivered;
        private int waitForComment;
        private int attentionCount;

        public int getWaitForPay() {
            return waitForPay;
        }

        public void setWaitForPay(int waitForPay) {
            this.waitForPay = waitForPay;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public int getWaitForDelivered() {
            return waitForDelivered;
        }

        public void setWaitForDelivered(int waitForDelivered) {
            this.waitForDelivered = waitForDelivered;
        }

        public int getDelivered() {
            return delivered;
        }

        public void setDelivered(int delivered) {
            this.delivered = delivered;
        }

        public int getWaitForComment() {
            return waitForComment;
        }

        public void setWaitForComment(int waitForComment) {
            this.waitForComment = waitForComment;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }
    }
}
