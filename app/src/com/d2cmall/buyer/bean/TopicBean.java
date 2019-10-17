package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/8/18.
 */

public class TopicBean extends BaseBean implements Serializable{

    /**
     * data : {"topics":{"next":false,"total":6,"previous":false,"index":1,"pageSize":40,"list":[{"shareCount":2,"id":2,"pic":"/2017/09/02/070213b9c328169cb5b834a81d41401cef2dca.png","title":"七夕单身","content":"<p>七夕，一个传统的节日<\/p><p>一个情侣间剁手的节日<\/p><p>一个床单花儿红的节日<\/p><p><img src=\"http://img.d2c.cn/2017/08/28/075339601ddca070efdec8792c3309145c3095.jpg\" style=\"font-family: inherit;\"><\/p>"},{"shareCount":0,"id":7,"pic":"/2017/09/02/070706ec81175ae7d00e85e6fe1683e7b3dd3e.png","title":"我的清爽look","content":null},{"shareCount":0,"id":6,"pic":"/2017/09/02/070614a7639bf63976b7e1db4b52a3ae2e621f.png","title":"条纹控","content":null},{"shareCount":0,"id":5,"pic":"/2017/09/02/0706271f0e1c67f1237dff4d5d4806f389ed45.png","title":"最美买家秀","content":null},{"shareCount":0,"id":4,"pic":"/2017/09/02/070154d3f2d81494f7d171057de103e8a5c46e.png","title":"夏日最酷装扮","content":"<p>全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建<br><\/p>"},{"shareCount":0,"id":3,"pic":"/2017/09/02/070208e9fd92ab75b823e9c62ab1af2fb639f1.png","title":"秀出好身材","content":"<p>祝所有D2C的用户小伙伴们与家人有个欢乐的中秋节；<\/p><p>我们特地定制了一款D2C的月饼，<\/p><p>当然今年是没有五仁的，<\/p><p>哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈<\/p>"}]}}
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
         * topics : {"next":false,"total":6,"previous":false,"index":1,"pageSize":40,"list":[{"shareCount":2,"id":2,"pic":"/2017/09/02/070213b9c328169cb5b834a81d41401cef2dca.png","title":"七夕单身","content":"<p>七夕，一个传统的节日<\/p><p>一个情侣间剁手的节日<\/p><p>一个床单花儿红的节日<\/p><p><img src=\"http://img.d2c.cn/2017/08/28/075339601ddca070efdec8792c3309145c3095.jpg\" style=\"font-family: inherit;\"><\/p>"},{"shareCount":0,"id":7,"pic":"/2017/09/02/070706ec81175ae7d00e85e6fe1683e7b3dd3e.png","title":"我的清爽look","content":null},{"shareCount":0,"id":6,"pic":"/2017/09/02/070614a7639bf63976b7e1db4b52a3ae2e621f.png","title":"条纹控","content":null},{"shareCount":0,"id":5,"pic":"/2017/09/02/0706271f0e1c67f1237dff4d5d4806f389ed45.png","title":"最美买家秀","content":null},{"shareCount":0,"id":4,"pic":"/2017/09/02/070154d3f2d81494f7d171057de103e8a5c46e.png","title":"夏日最酷装扮","content":"<p>全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建<br><\/p>"},{"shareCount":0,"id":3,"pic":"/2017/09/02/070208e9fd92ab75b823e9c62ab1af2fb639f1.png","title":"秀出好身材","content":"<p>祝所有D2C的用户小伙伴们与家人有个欢乐的中秋节；<\/p><p>我们特地定制了一款D2C的月饼，<\/p><p>当然今年是没有五仁的，<\/p><p>哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈<\/p>"}]}
         */

        private TopicsBean topics;

        public TopicsBean getTopics() {
            return topics;
        }

        public void setTopics(TopicsBean topics) {
            this.topics = topics;
        }

        public static class TopicsBean implements Serializable{
            /**
             * next : false
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"shareCount":2,"id":2,"pic":"/2017/09/02/070213b9c328169cb5b834a81d41401cef2dca.png","title":"七夕单身","content":"<p>七夕，一个传统的节日<\/p><p>一个情侣间剁手的节日<\/p><p>一个床单花儿红的节日<\/p><p><img src=\"http://img.d2c.cn/2017/08/28/075339601ddca070efdec8792c3309145c3095.jpg\" style=\"font-family: inherit;\"><\/p>"},{"shareCount":0,"id":7,"pic":"/2017/09/02/070706ec81175ae7d00e85e6fe1683e7b3dd3e.png","title":"我的清爽look","content":null},{"shareCount":0,"id":6,"pic":"/2017/09/02/070614a7639bf63976b7e1db4b52a3ae2e621f.png","title":"条纹控","content":null},{"shareCount":0,"id":5,"pic":"/2017/09/02/0706271f0e1c67f1237dff4d5d4806f389ed45.png","title":"最美买家秀","content":null},{"shareCount":0,"id":4,"pic":"/2017/09/02/070154d3f2d81494f7d171057de103e8a5c46e.png","title":"夏日最酷装扮","content":"<p>全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建全球设计师集成平台是一家集潮流风尚、前沿艺术、个性设计为一体的设计师平台。中国首家全球设计师集成平台于2011年3月创建<br><\/p>"},{"shareCount":0,"id":3,"pic":"/2017/09/02/070208e9fd92ab75b823e9c62ab1af2fb639f1.png","title":"秀出好身材","content":"<p>祝所有D2C的用户小伙伴们与家人有个欢乐的中秋节；<\/p><p>我们特地定制了一款D2C的月饼，<\/p><p>当然今年是没有五仁的，<\/p><p>哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈<\/p>"}]
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

            public static class ListBean implements Serializable{
                /**
                 * shareCount : 2
                 * id : 2
                 * pic : /2017/09/02/070213b9c328169cb5b834a81d41401cef2dca.png
                 * title : 七夕单身
                 * content : <p>七夕，一个传统的节日</p><p>一个情侣间剁手的节日</p><p>一个床单花儿红的节日</p><p><img src="http://img.d2c.cn/2017/08/28/075339601ddca070efdec8792c3309145c3095.jpg" style="font-family: inherit;"></p>
                 */

                private int shareCount;
                private int id;
                private String pic;
                private String title;
                private String content;

                public int getShareCount() {
                    return shareCount;
                }

                public void setShareCount(int shareCount) {
                    this.shareCount = shareCount;
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
