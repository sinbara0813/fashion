package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 返利率
 * Author: Blend
 * Date: 2016/08/17 15:30
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class RatioBean extends BaseBean {

    /**
     * ratio : 12.0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private double ratio;

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }
    }
}
