package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/19 15:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageUnreadBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int unreadCount;


        public int getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }


    }
}
