package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 版本更新
 * Author: hrb
 * Date: 2016/10/14 16:08
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateBean extends BaseBean {

    /**
     * value : {"url":"https://itunes.apple.com/us/app/d2c-quan-qiu-she-ji-shi-ji/id980211165?l=zh&ls=1&mt=8","official":"经过小伙伴们的不努力, 为亲呈现了别样的小D,超过过两行了吗","must":"0","newestAppVersion":"2.2.3"}
     * status : 1
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String value;
        private int isUpgrade;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIsUpgrade() {
            return isUpgrade;
        }

        public void setIsUpgrade(int isUpgrade) {
            this.isUpgrade = isUpgrade;
        }
    }
}
