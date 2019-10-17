package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Fixme
 * Author: hrb
 * Date: 2016/12/12 14:53
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RongTokenBean extends BaseBean {

    /**
     * token : FEL1tcqTAaU0xdLXjzt79uxMnK4yuQcd02v9yVDJYoToVwVHchYWDsEq7Ks4wKiKIymhtVlKfZtYFw9mBTIvYO4WIi3IVXLd
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
