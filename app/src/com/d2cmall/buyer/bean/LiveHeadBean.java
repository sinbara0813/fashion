package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2018/1/2.
 */

public class LiveHeadBean extends BaseBean {
    private String headPicUrl;
    private long userId;

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
