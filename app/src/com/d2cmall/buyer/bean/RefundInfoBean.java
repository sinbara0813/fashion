package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 退款详情bean类
 * Author: Blend
 * Date: 16/4/21 13:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RefundInfoBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private RefundsBean.DataEntity.RefundsEntity.ListEntity refund;

        public RefundsBean.DataEntity.RefundsEntity.ListEntity getRefund() {
            return refund;
        }

        public void setRefund(RefundsBean.DataEntity.RefundsEntity.ListEntity refund) {
            this.refund = refund;
        }
    }
}
