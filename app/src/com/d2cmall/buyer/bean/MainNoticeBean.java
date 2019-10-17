package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/3/23.
 * Description : MainNoticeBean//首页公告接口
 */

public class MainNoticeBean extends BaseBean {

    /**
     * data : {"article":{"hits":0,"articleTemplateId":27,"name":"sxsxd","publishDate":1522142237000,"id":11970,"title":"黄金时间","picture":"/2018/03/27/09175899cf10460f7a693ad257a21e67c81ecd.jpg","content":null}}
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
         * article : {"hits":0,"articleTemplateId":27,"name":"sxsxd","publishDate":1522142237000,"id":11970,"title":"黄金时间","picture":"/2018/03/27/09175899cf10460f7a693ad257a21e67c81ecd.jpg","content":null}
         */

        private ArticleBean article;

        public ArticleBean getArticle() {
            return article;
        }

        public void setArticle(ArticleBean article) {
            this.article = article;
        }

        public static class ArticleBean {
            /**
             * hits : 0
             * articleTemplateId : 27
             * name : sxsxd
             * publishDate : 1522142237000
             * id : 11970
             * title : 黄金时间
             * picture : /2018/03/27/09175899cf10460f7a693ad257a21e67c81ecd.jpg
             * content : null
             */

            private int hits;
            private int articleTemplateId;
            private String name;
            private long publishDate;
            private int id;
            private String title;
            private String picture;
            private String content;

            public int getHits() {
                return hits;
            }

            public void setHits(int hits) {
                this.hits = hits;
            }

            public int getArticleTemplateId() {
                return articleTemplateId;
            }

            public void setArticleTemplateId(int articleTemplateId) {
                this.articleTemplateId = articleTemplateId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(long publishDate) {
                this.publishDate = publishDate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
