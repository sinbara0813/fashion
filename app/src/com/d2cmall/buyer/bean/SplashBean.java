package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 闪屏Bean
 * Author: Blend
 * Date: 2016/10/17 19:02
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class SplashBean extends BaseBean {
    /**
     * splashscreen : {"id":5,"name":"te","version":"5_1476689182000","pics":["/2016/10/10/09053058136748c84142e15c219b809d012383.jpg"]}
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
         * id : 5
         * name : te
         * version : 5_1476689182000
         * pics : ["/2016/10/10/09053058136748c84142e15c219b809d012383.jpg"]
         */

        private SplashscreenBean splashscreen;

        public SplashscreenBean getSplashscreen() {
            return splashscreen;
        }

        public void setSplashscreen(SplashscreenBean splashscreen) {
            this.splashscreen = splashscreen;
        }

        public static class SplashscreenBean {
            private int id;
            private String name;
            private List<String> pics;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getPics() {
                return pics;
            }

            public void setPics(List<String> pics) {
                this.pics = pics;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
