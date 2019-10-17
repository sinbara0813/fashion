package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 退款换货详情bean类
 * Author: Blend
 * Date: 16/4/21 13:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ReshipInfoBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ReshipsBean.DataBean.ReshipsDataBean.ListBean reship;

        public ReshipsBean.DataBean.ReshipsDataBean.ListBean getReship() {
            return reship;
        }

        public void setReship(ReshipsBean.DataBean.ReshipsDataBean.ListBean reship) {
            this.reship = reship;
        }
    }
}
