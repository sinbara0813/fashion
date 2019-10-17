package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 订单详情bean类
 * Author: Blend
 * Date: 16/4/15 15:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class OrderInfoBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private OrdersBean.DataEntity.OrdersEntity.ListEntity order;

        public OrdersBean.DataEntity.OrdersEntity.ListEntity getOrder() {
            return order;
        }

        public void setOrder(OrdersBean.DataEntity.OrdersEntity.ListEntity order) {
            this.order = order;
        }

        private CollageDetailBean.DataBean.CollageGroupBean.CollageOrder collageOrder;

        public CollageDetailBean.DataBean.CollageGroupBean.CollageOrder getCollageOrder() {
            return collageOrder;
        }

        public void setCollageOrder(CollageDetailBean.DataBean.CollageGroupBean.CollageOrder collageOrder) {
            this.collageOrder = collageOrder;
        }
    }
}
