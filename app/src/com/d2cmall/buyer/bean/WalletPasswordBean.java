package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * WalletPasswordBean
 * Author: hrb
 * Date: 2016/11/22 13:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class WalletPasswordBean extends BaseBean {
    /**
     * data : {"errorTimes":5}
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
         * errorTimes : 5
         */

        private int errorTimes;

        public int getErrorTimes() {
            return errorTimes;
        }

        public void setErrorTimes(int errorTimes) {
            this.errorTimes = errorTimes;
        }
    }
}
