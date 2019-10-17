package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/5/22 17:33
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CheckOrderSkuBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<AvailableBean> disable;
        private List<AvailableBean> available;

        public List<AvailableBean> getDisable() {
            return disable;
        }

        public void setDisable(List<AvailableBean> disable) {
            this.disable = disable;
        }

        public List<AvailableBean> getAvailable() {
            return available;
        }

        public void setAvailable(List<AvailableBean> available) {
            this.available = available;
        }

        public static class AvailableBean {

            private String img;
            private long skuId;//SKUID

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public long getSkuId() {
                return skuId;
            }

            public void setSkuId(long skuId) {
                this.skuId = skuId;
            }
        }
    }
}
