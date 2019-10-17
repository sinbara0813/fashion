package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 评论数量
 * Author: hrb
 * Date: 2016/07/16 10:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CommentNumBean extends BaseBean {

    /**
     * shareCount : 0
     * count : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int shareCount;
        private int count;
        private int commendId;

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCommendId() {
            return commendId;
        }

        public void setCommendId(int commendId) {
            this.commendId = commendId;
        }
    }
}
