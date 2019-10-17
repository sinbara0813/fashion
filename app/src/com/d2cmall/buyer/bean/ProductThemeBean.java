package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/22.
 */

public class ProductThemeBean extends BaseBean {

    /**
     * data : {"themes":[{"tagId":10,"recommend":null,"id":26,"pic":"/2017/09/11/03254602614eaa633d32c4c009de00574b9875.png","title":"爱尚","tagName":"穿衣搭配","url":"/update/20170922144923","createDate":0},{"tagId":11,"recommend":null,"id":23,"pic":"/2017/09/05/085423523db2144bb298e98958520fd228c775.png","title":"熟地黄","tagName":"男得好货","url":"/page/qixiqinghua","createDate":1504601515000}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ThemesBean> themes;

        public List<ThemesBean> getThemes() {
            return themes;
        }

        public void setThemes(List<ThemesBean> themes) {
            this.themes = themes;
        }

        public static class ThemesBean {
            /**
             * tagId : 10
             * recommend : null
             * id : 26
             * pic : /2017/09/11/03254602614eaa633d32c4c009de00574b9875.png
             * title : 爱尚
             * tagName : 穿衣搭配
             * url : /update/20170922144923
             * createDate : 0
             */

            private long tagId;
            private String recommend;
            private long id;
            private String pic;
            private String title;
            private String tagName;
            private String url;
            private String createDate;

            public long getTagId() {
                return tagId;
            }

            public void setTagId(long tagId) {
                this.tagId = tagId;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
