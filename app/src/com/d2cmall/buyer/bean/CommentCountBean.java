package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 商品评论数量
 * Author: hrb
 * Date: 2016/07/30 13:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CommentCountBean extends BaseBean {

    /**
     * data : {"shareCount":0,"picCount":0,"consultCount":0,"totalCount":0}
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
         * shareCount : 0
         * picCount : 0
         * consultCount : 0
         * totalCount : 0
         */

        private int shareCount;
        private int picCount;
        private int consultCount;
        private int totalCount;

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public int getPicCount() {
            return picCount;
        }

        public void setPicCount(int picCount) {
            this.picCount = picCount;
        }

        public int getConsultCount() {
            return consultCount;
        }

        public void setConsultCount(int consultCount) {
            this.consultCount = consultCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
