package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 设计师下的作品或买家秀数量
 * Author: Blend
 * Date: 2016/07/04 14:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DesignerCountBean extends BaseBean {

    /**
     * productCount : 0
     * shareCount : 0
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int productCount;
        private int shareCount;

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }
    }
}
