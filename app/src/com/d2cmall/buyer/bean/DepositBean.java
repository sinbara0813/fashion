package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Fixme 充值model
 * Author: hrb
 * Date: 2016/08/18 15:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DepositBean extends BaseBean {

    /**
     * sn :
     * payChannel :
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String sn;
        private String payChannel;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }
    }
}
