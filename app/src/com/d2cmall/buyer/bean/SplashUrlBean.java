package com.d2cmall.buyer.bean;

/**
 * 闪屏Bean
 * Author: PengHong
 * Date: 2016/12/07
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class SplashUrlBean {

    private String pic;
    private String url;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SplashUrlBean{" +
                "pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
