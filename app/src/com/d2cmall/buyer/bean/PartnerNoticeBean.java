package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 * Description : PartnerNoticeBean
 */

public class PartnerNoticeBean extends BaseBean {

    /**
     * data : {"articleList":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"hits":0,"articleTemplateId":27,"name":"purcharse","publishDate":1517369307000,"id":11961,"title":"测试一下","picture":"/2018/01/30/07181817a935047dd82c0a6f8e9175764fbc35.jpg","content":null}]}}
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
         * articleList : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"hits":0,"articleTemplateId":27,"name":"purcharse","publishDate":1517369307000,"id":11961,"title":"测试一下","picture":"/2018/01/30/07181817a935047dd82c0a6f8e9175764fbc35.jpg","content":null}]}
         */

        private ArticleListBean articleList;

        public ArticleListBean getArticleList() {
            return articleList;
        }

        public void setArticleList(ArticleListBean articleList) {
            this.articleList = articleList;
        }

        public static class ArticleListBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"hits":0,"articleTemplateId":27,"name":"purcharse","publishDate":1517369307000,"id":11961,"title":"测试一下","picture":"/2018/01/30/07181817a935047dd82c0a6f8e9175764fbc35.jpg","content":null}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * hits : 0
                 * articleTemplateId : 27
                 * name : purcharse
                 * publishDate : 1517369307000
                 * id : 11961
                 * title : 测试一下
                 * picture : /2018/01/30/07181817a935047dd82c0a6f8e9175764fbc35.jpg
                 * content : null
                 */

                private int hits;
                private int articleTemplateId;
                private String name;
                private Date publishDate;
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

                public Date getPublishDate() {
                    return publishDate;
                }

                public void setPublishDate(Date publishDate) {
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
}
