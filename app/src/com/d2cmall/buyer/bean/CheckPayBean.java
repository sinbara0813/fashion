package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/5/24 11:01
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CheckPayBean extends BaseBean {

    /**
     * data : {"danger":1}
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
         * danger : 1
         */

        private int danger;

        public int getDanger() {
            return danger;
        }

        public void setDanger(int danger) {
            this.danger = danger;
        }
    }
}
