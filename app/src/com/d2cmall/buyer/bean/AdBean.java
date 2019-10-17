package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2017/9/23.
 */

public class AdBean extends BaseBean {

    /**
     * data : {"adResource":{"id":12,"pic":"/2017/09/07/083545330855671d886c867900469643b06480.png","title":"123","url":"/product/163935"}}
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
         * adResource : {"id":12,"pic":"/2017/09/07/083545330855671d886c867900469643b06480.png","title":"123","url":"/product/163935"}
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
             * id : 12
             * pic : /2017/09/07/083545330855671d886c867900469643b06480.png
             * title : 123
             * url : /product/163935
             */

            private int id;
            private String pic;
            private String title;
            private String url;
            private String[] pics;
            private String[] picsUrl;

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

            public String[] getPics() {
                return pics;
            }

            public void setPics(String[] pics) {
                this.pics = pics;
            }

            public String[] getPicsUrl() {
                return picsUrl;
            }

            public void setPicsUrl(String[] picsUrl) {
                this.picsUrl = picsUrl;
            }
        }
    }
}
