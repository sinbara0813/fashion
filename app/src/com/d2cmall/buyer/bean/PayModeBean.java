package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/7/6 11:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PayModeBean extends BaseBean {

    /**
     * data : {"channels":["WALLET","MIMEPAY"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String interestHolder; //0用户 1商家
        private List<String> channels;

        public List<String> getChannels() {
            return channels;
        }

        public void setChannels(List<String> channels) {
            this.channels = channels;
        }

        public String getInterestHolder() {
            return interestHolder;
        }

        public void setInterestHolder(String interestHolder) {
            this.interestHolder = interestHolder;
        }
    }
}
