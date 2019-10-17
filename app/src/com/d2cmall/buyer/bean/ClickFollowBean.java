package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * ClickFollowBean
 * Author: Blend
 * Date: 2017/03/14 16:05
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class ClickFollowBean extends BaseBean {

    /**
     * data : {"follow":1,"friends":0}
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
         * follow : 1
         * friends : 0
         */

        private int follow;

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }


    }
}
