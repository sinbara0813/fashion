package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/8.
 */

public class NavigationLeftBean extends BaseBean {


    /**
     * data : {"navigations":[{"picUrl":"","name":"热门","id":15323,"pic":"","url":"111","items":[{"id":13,"pic":"/model/1603/f933c1ab782e548d3fe8e61139ed8eb1","title":"上装","url":"/product/list?t=1&c=20"},{"id":12,"pic":"","title":"裤装","url":"/product/list?t=1&c=30"},{"id":66,"pic":"","title":"裙装","url":"/product/list?t=1&c=10"},{"id":90,"pic":"","title":"外套","url":"/product/list?t=1&c=1692"},{"id":92,"pic":"","title":"内衣","url":"/product/list?t=1&c=1680"},{"id":310,"pic":"/2016/08/30/073029f54f93d53ef83b656d14b6caf2fb1263.jpg","title":"连衣裙","url":"/product/list?t=1&c=1588"},{"id":311,"pic":"/2016/08/30/073315ca84a87659ba4d1d61be77970e2fc127.jpg","title":"半身裙","url":"/product/list?t=1&c=1684"},{"id":312,"pic":"/2016/08/30/0735060d504759df77c3ffdba7714d340cfa56.jpg","title":"衬衫","url":"/product/list?t=1&c=1605"}]},{"picUrl":"","name":"女士","id":15273,"pic":"/2017/08/04/031249641d42bf5713ed2085f65e5aa3887859.jpg","items":[{"id":13,"pic":"/model/1603/f933c1ab782e548d3fe8e61139ed8eb1","title":"上装","url":"/product/list?t=1&c=20"},{"id":12,"pic":"","title":"裤装","url":"/product/list?t=1&c=30"},{"id":66,"pic":"","title":"裙装","url":"/product/list?t=1&c=10"},{"id":90,"pic":"","title":"外套","url":"/product/list?t=1&c=1692"},{"id":92,"pic":"","title":"内衣","url":"/product/list?t=1&c=1680"},{"id":310,"pic":"/2016/08/30/073029f54f93d53ef83b656d14b6caf2fb1263.jpg","title":"连衣裙","url":"/product/list?t=1&c=1588"},{"id":311,"pic":"/2016/08/30/073315ca84a87659ba4d1d61be77970e2fc127.jpg","title":"半身裙","url":"/product/list?t=1&c=1684"},{"id":312,"pic":"/2016/08/30/0735060d504759df77c3ffdba7714d340cfa56.jpg","title":"衬衫","url":"/product/list?t=1&c=1605"}],"url":"/page/YouppiexMLP"},{},{},{},{},{}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<NavigationsBean> navigations;

        public List<NavigationsBean> getNavigations() {
            return navigations;
        }

        public void setNavigations(List<NavigationsBean> navigations) {
            this.navigations = navigations;
        }

        public static class NavigationsBean {
            /**
             * picUrl :
             * name : 热门
             * id : 15323
             * pic :
             * url : 111
             * items : [{"id":13,"pic":"/model/1603/f933c1ab782e548d3fe8e61139ed8eb1","title":"上装","url":"/product/list?t=1&c=20"},{"id":12,"pic":"","title":"裤装","url":"/product/list?t=1&c=30"},{"id":66,"pic":"","title":"裙装","url":"/product/list?t=1&c=10"},{"id":90,"pic":"","title":"外套","url":"/product/list?t=1&c=1692"},{"id":92,"pic":"","title":"内衣","url":"/product/list?t=1&c=1680"},{"id":310,"pic":"/2016/08/30/073029f54f93d53ef83b656d14b6caf2fb1263.jpg","title":"连衣裙","url":"/product/list?t=1&c=1588"},{"id":311,"pic":"/2016/08/30/073315ca84a87659ba4d1d61be77970e2fc127.jpg","title":"半身裙","url":"/product/list?t=1&c=1684"},{"id":312,"pic":"/2016/08/30/0735060d504759df77c3ffdba7714d340cfa56.jpg","title":"衬衫","url":"/product/list?t=1&c=1605"}]
             */

            private String picUrl;
            private String name;
            private int id;
            private String pic;
            private String url;
            private List<ItemsBean> items;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * id : 13
                 * pic : /model/1603/f933c1ab782e548d3fe8e61139ed8eb1
                 * title : 上装
                 * url : /product/list?t=1&c=20
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
}
