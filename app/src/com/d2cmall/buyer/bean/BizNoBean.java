package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/8/28.
 * Description : BizNoBean
 */

public class BizNoBean extends BaseBean {

    /**
     * data : {"bizNo":"ZM201808283000000696900736588085"}
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
         * bizNo : ZM201808283000000696900736588085
         */

        private String bizNo;

        public String getBizNo() {
            return bizNo;
        }

        public void setBizNo(String bizNo) {
            this.bizNo = bizNo;
        }
    }
}
