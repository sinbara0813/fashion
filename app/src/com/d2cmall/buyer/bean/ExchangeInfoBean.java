package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 换货详情bean类
 * Author: Blend
 * Date: 16/4/21 13:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExchangeInfoBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ExchangesBean.DataBean.ExchangeListBean.ListBean exchange;

        public ExchangesBean.DataBean.ExchangeListBean.ListBean getExchange() {
            return exchange;
        }

        public void setExchange(ExchangesBean.DataBean.ExchangeListBean.ListBean exchange) {
            this.exchange = exchange;
        }
    }
}
