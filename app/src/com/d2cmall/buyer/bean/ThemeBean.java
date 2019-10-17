package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/7.
 */

public class ThemeBean extends BaseBean {

    /**
     * data : {"themes":{"next":false,"total":4,"previous":false,"index":1,"pageSize":40,"list":[{"tagId":4,"recommend":null,"id":20,"pic":"/2017/09/05/0820174391963a493c47e8a01b7d12397e5013.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":21,"pic":"/2017/09/05/08233302614eaa633d32c4c009de00574b9875.png","title":"假日度假风来袭","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":22,"pic":"/2017/09/05/082500a5fefb38667efeebcb97482f70421a7d.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":11,"recommend":null,"id":23,"pic":"/2017/09/05/085423523db2144bb298e98958520fd228c775.png","title":"熟地黄","tagName":"男得好货","url":"/page/qixiqinghua","createDate":0}]}}
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
         * themes : {"next":false,"total":4,"previous":false,"index":1,"pageSize":40,"list":[{"tagId":4,"recommend":null,"id":20,"pic":"/2017/09/05/0820174391963a493c47e8a01b7d12397e5013.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":21,"pic":"/2017/09/05/08233302614eaa633d32c4c009de00574b9875.png","title":"假日度假风来袭","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":22,"pic":"/2017/09/05/082500a5fefb38667efeebcb97482f70421a7d.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":11,"recommend":null,"id":23,"pic":"/2017/09/05/085423523db2144bb298e98958520fd228c775.png","title":"熟地黄","tagName":"男得好货","url":"/page/qixiqinghua","createDate":0}]}
         */

        private ThemesBean themes;

        public ThemesBean getThemes() {
            return themes;
        }

        public void setThemes(ThemesBean themes) {
            this.themes = themes;
        }

        public static class ThemesBean {
            /**
             * next : false
             * total : 4
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"tagId":4,"recommend":null,"id":20,"pic":"/2017/09/05/0820174391963a493c47e8a01b7d12397e5013.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":21,"pic":"/2017/09/05/08233302614eaa633d32c4c009de00574b9875.png","title":"假日度假风来袭","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":4,"recommend":null,"id":22,"pic":"/2017/09/05/082500a5fefb38667efeebcb97482f70421a7d.png","title":"2017最时尚元素","tagName":"达人推荐","url":"/page/qixiqinghua","createDate":0},{"tagId":11,"recommend":null,"id":23,"pic":"/2017/09/05/085423523db2144bb298e98958520fd228c775.png","title":"熟地黄","tagName":"男得好货","url":"/page/qixiqinghua","createDate":0}]
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
                 * tagId : 4
                 * recommend : null
                 * id : 20
                 * pic : /2017/09/05/0820174391963a493c47e8a01b7d12397e5013.png
                 * title : 2017最时尚元素
                 * tagName : 达人推荐
                 * url : /page/qixiqinghua
                 * createDate : 0
                 */

                private int tagId;
                private String recommend;
                private int id;
                private String pic;
                private String title;
                private String tagName;
                private String url;
                private String createDate;

                public int getTagId() {
                    return tagId;
                }

                public void setTagId(int tagId) {
                    this.tagId = tagId;
                }

                public String getRecommend() {
                    return recommend;
                }

                public void setRecommend(String recommend) {
                    this.recommend = recommend;
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
}
