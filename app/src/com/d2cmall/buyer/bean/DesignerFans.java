package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Author: PengHong
 * Date: 2017/01/10 19:35
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class DesignerFans extends BaseBean {

    /**
     * totalCount : 4
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int totalCount;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
