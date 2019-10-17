package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/17 13:10
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class AdShareBean extends BaseBean {

    /**
     * data : {"adResource":{"id":6,"pic":"/2017/09/25/032816f2daa292b072f013c0c43d94334a8d21.jpg","title":"abc1","url":"/update/20171017124816"}}
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
         * adResource : {"id":6,"pic":"/2017/09/25/032816f2daa292b072f013c0c43d94334a8d21.jpg","title":"abc1","url":"/update/20171017124816"}
         */

        private AdResourceBean adResource;

        public AdResourceBean getAdResource() {
            return adResource;
        }

        public void setAdResource(AdResourceBean adResource) {
            this.adResource = adResource;
        }

        public static class AdResourceBean {
            /**
             * id : 6
             * pic : /2017/09/25/032816f2daa292b072f013c0c43d94334a8d21.jpg
             * title : abc1
             * url : /update/20171017124816
             */

            private int id;
            private String pic;
            private String title;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
