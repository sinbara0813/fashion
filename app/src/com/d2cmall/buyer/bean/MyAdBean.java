package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/12/13.
 */

public class MyAdBean extends BaseBean {

    /**
     * data : {"adResource":{"picsUrl":["5465"],"id":29,"pic":"/2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg","title":"测试一下","pics":["/2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg"],"url":"5465"}}
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
         * adResource : {"picsUrl":["5465"],"id":29,"pic":"/2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg","title":"测试一下","pics":["/2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg"],"url":"5465"}
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
             * picsUrl : ["5465"]
             * id : 29
             * pic : /2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg
             * title : 测试一下
             * pics : ["/2017/12/13/081820e85f9b190143572f682ae09b5636ccae.jpg"]
             * url : 5465
             */

            private int id;
            private String pic;
            private String title;
            private String url;
            private List<String> picsUrl;
            private List<String> pics;

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

            public List<String> getPicsUrl() {
                return picsUrl;
            }

            public void setPicsUrl(List<String> picsUrl) {
                this.picsUrl = picsUrl;
            }

            public List<String> getPics() {
                return pics;
            }

            public void setPics(List<String> pics) {
                this.pics = pics;
            }
        }
    }
}
