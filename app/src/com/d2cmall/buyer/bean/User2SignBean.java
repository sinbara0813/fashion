package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/4/25 17:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class User2SignBean extends BaseBean {

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

    private UserSignBean.DataBean datas;

    public UserSignBean.DataBean getDatas() {
        return datas;
    }

    public void setDatas(UserSignBean.DataBean data) {
        this.datas = data;
    }

    public static class DataBean {
        /**
         * thdUserId : 43B7CFB5B47DB885DFFCDDEB74EBB9030CF846FB7E1CE479318D2902909B6837
         */

        private String thdUserId;

        public String getThdUserId() {
            return thdUserId;
        }

        public void setThdUserId(String thdUserId) {
            this.thdUserId = thdUserId;
        }
    }
}
