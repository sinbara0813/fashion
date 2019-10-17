package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 * Description : DCionShopBean
 */

public class DCionShopBean extends BaseBean {

    /**
     * data : {"adResource":{"shotTitles":null,"picsUrl":["http://www.baidu.com"],"id":12,"pic":"/2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg","video":null,"title":"积分商城","pics":["/2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg"],"descriptions":null,"url":"http://www.baidu.com"}}
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
         * adResource : {"shotTitles":null,"picsUrl":["http://www.baidu.com"],"id":12,"pic":"/2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg","video":null,"title":"积分商城","pics":["/2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg"],"descriptions":null,"url":"http://www.baidu.com"}
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
             * shotTitles : null
             * picsUrl : ["http://www.baidu.com"]
             * id : 12
             * pic : /2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg
             * video : null
             * title : 积分商城
             * pics : ["/2018/08/09/02453817a935047dd82c0a6f8e9175764fbc35.jpg"]
             * descriptions : null
             * url : http://www.baidu.com
             */

            private String shotTitles;
            private int id;
            private String pic;
            private String video;
            private String title;
            private String descriptions;
            private String url;
            private List<String> picsUrl;
            private List<String> pics;

            public String getShotTitles() {
                return shotTitles;
            }

            public void setShotTitles(String shotTitles) {
                this.shotTitles = shotTitles;
            }

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

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescriptions() {
                return descriptions;
            }

            public void setDescriptions(String descriptions) {
                this.descriptions = descriptions;
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
