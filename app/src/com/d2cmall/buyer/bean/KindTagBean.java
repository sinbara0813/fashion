package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class KindTagBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 15273
         * name : 女士
         * url : /product/list?t=1
         * items : [{"id":13,"title":"上装","url":"/product/list?t=1&c=20"},{"id":12,"title":"裤装","url":"/product/list?t=1&c=30"},{"id":66,"title":"裙装","url":"/product/list?t=1&c=10"},{"id":90,"title":"外套","url":"/product/list?t=1&c=1692"},{"id":92,"title":"内衣","url":"/product/list?t=1&c=1680"}]
         */

        private List<NavigationsEntity> navigations;

        public void setNavigations(List<NavigationsEntity> navigations) {
            this.navigations = navigations;
        }

        public List<NavigationsEntity> getNavigations() {
            return navigations;
        }

        public static class NavigationsEntity {
            private int id;
            private String name;
            private String url;
            private String pic;
            private String picUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            /**
             * id : 13
             * title : 上装
             * url : /product/list?t=1&c=20
             */

            private List<ItemsEntity> items;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                private int id;
                private String title;
                private String url;

                public void setId(int id) {
                    this.id = id;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getId() {
                    return id;
                }

                public String getTitle() {
                    return title;
                }

                public String getUrl() {
                    return url;
                }
            }
        }
    }
}
