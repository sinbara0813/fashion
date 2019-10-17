package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;


/**
 * Created by Administrator on 2018/8/15.
 * Description : MyPointBean
 */

public class MyPointBean extends BaseBean {


    /**
     * data : {"point":8}
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
         * point : 8
         */

        private int point;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }
    }
}
