package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;


public class BindRecommendBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private long recId;

        public long getRecId() {
            return recId;
        }

        public void setRecId(long recId) {
            this.recId = recId;
        }
    }
}
