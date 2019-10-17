package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2018/3/9.
 */

public class FootMarkCountBean extends BaseBean {

    /**
     * data : {"products":0}
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
         * products : 0
         */

        private int products;

        public int getProducts() {
            return products;
        }

        public void setProducts(int products) {
            this.products = products;
        }
    }
}
