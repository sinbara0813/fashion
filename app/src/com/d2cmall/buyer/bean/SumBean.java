package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/15 18:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SumBean extends BaseBean {

    /**
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private ProductDataBean productData;

        public ProductDataBean getProductData() {
            return productData;
        }

        public void setProductData(ProductDataBean productData) {
            this.productData = productData;
        }

        public static class ProductDataBean {

            private String summary;
            private int id;

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
