package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/20 19:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UserSignBean extends BaseBean {

    /**
     * message : 操作成功
     * datas : {"thdUserId":"43B7CFB5B47DB885DFFCDDEB74EBB9030CF846FB7E1CE479318D2902909B6837"}
     * list : []
     * total : 1
     * index : 1
     * pageCount : 1
     * pageSize : 1
     * next : false
     */

    private DataBean data;

    public DataBean getDatas() {
        return data;
    }

    public void setDatas(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * thdUserId : 43B7CFB5B47DB885DFFCDDEB74EBB9030CF846FB7E1CE479318D2902909B6837
         */

        private String thdUserId;

        private String orderSn;

        private String url;

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getThdUserId() {
            return thdUserId;
        }

        public void setThdUserId(String thdUserId) {
            this.thdUserId = thdUserId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
